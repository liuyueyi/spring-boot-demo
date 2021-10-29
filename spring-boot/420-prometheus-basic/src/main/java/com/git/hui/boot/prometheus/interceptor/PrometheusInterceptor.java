package com.git.hui.boot.prometheus.interceptor;

import io.prometheus.client.Histogram;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuzebang
 * @date 2021/9/29
 */
public class PrometheusInterceptor extends HandlerInterceptorAdapter {

    private ThreadLocal<Histogram.Timer> timerThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 正在处理的请求量
        PrometheusComponent.getInstance().gauge().labels(request.getRequestURI(), request.getMethod()).inc();

        timerThreadLocal.set(PrometheusComponent.getInstance().histogram()
                .labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus()))
                .startTimer());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();
        // count 请求计数，标签分别为 请求路径，请求方法，response http code
        // 请求应用总量:  sum(demo_rest_req_total)
        // 每秒http请求量: sum(rate(demo_rest_req_total[1m])
        // 请求topk的url:  topk(10, sum(demo_rest_req_total) by (path))
        PrometheusComponent.getInstance().counter().labels(uri, method, String.valueOf(status)).inc();

        // 请求完毕，计数器-1
        PrometheusComponent.getInstance().gauge().labels(uri, method).dec();

        // 直方图统计
        Histogram.Timer timer = timerThreadLocal.get();
        if (timer != null) {
            timer.observeDuration();
            timerThreadLocal.remove();
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
