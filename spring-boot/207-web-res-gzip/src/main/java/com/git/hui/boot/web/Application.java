package com.git.hui.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author YiHui
 * @date 2023/11/6
 */
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    /**
     * 配置返回的静态资源的压缩与缓存方式
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePrivate())
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    /**
     * 所有的返回结果，包装一个 content-length 返回
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new AddContentLengthFilter());
        filterBean.setUrlPatterns(Arrays.asList("*"));
        return filterBean;
    }

    /**
     * 现象：当返回的是json对象时， server.compression.min-response-size不起作用，不管这个对象的大小，默认全部做gzip压缩。
     * 原因：
     * - 当返回的是字符串，即Content-Type: text/plain 时，会设置Content-Length，则会根据实际返回的大小来判断是否需要进行gzip压缩
     * - 而当返回的是对象，即Content-Type: application/json时，不会设置Content-Length，服务端无法判断长度，并且是通过Transfer-Encoding: chunked的方式发送给客户端，因此一定会做压缩。
     * 解决方案:
     * - 加上全局的 content-length
     */
    class AddContentLengthFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // ContentCachingResponseWrapper会缓存所有写给OutputStream的数据，并且因为缓存了内容，所以可以获取Content-Length并帮忙设置
            ContentCachingResponseWrapper cacheResponseWrapper;
            if (response instanceof ContentCachingResponseWrapper) {
                cacheResponseWrapper = (ContentCachingResponseWrapper) response;
            } else {
                cacheResponseWrapper = new ContentCachingResponseWrapper(response);
            }

            filterChain.doFilter(request, cacheResponseWrapper);
            cacheResponseWrapper.copyBodyToResponse();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
