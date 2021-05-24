package com.git.hui.boot.aop.logaspect;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wuzebang
 * @date 2021/5/24
 */
@Configuration
@Component
public class ExtendLogAspect {

    public static class LogAdvice implements MethodInterceptor {
        private static final String SPLIT_SYMBOL = "|";

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Object res = null;
            String req = null;
            long start = System.currentTimeMillis();
            try {
                req = buildReqLog(methodInvocation);
                res = methodInvocation.proceed();
                return res;
            } catch (Throwable e) {
                res = "Un-Expect-Error";
                throw e;
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(req + "" + JSON.toJSONString(res) + SPLIT_SYMBOL + (end - start));
            }
        }


        private String buildReqLog(MethodInvocation joinPoint) {
            // 目标对象
            Object target = joinPoint.getThis();
            // 执行的方法
            Method method = joinPoint.getMethod();
            // 请求参数
            Object[] args = joinPoint.getArguments();

            StringBuilder builder = new StringBuilder(target.getClass().getName());
            builder.append(SPLIT_SYMBOL).append(method.getName()).append(SPLIT_SYMBOL);
            for (Object arg : args) {
                builder.append(JSON.toJSONString(arg)).append(",");
            }
            return builder.substring(0, builder.length() - 1) + SPLIT_SYMBOL;
        }
    }

    public static class LogAdvisor extends AbstractBeanFactoryPointcutAdvisor {
        @Setter
        private Pointcut logPointCut;

        @Override
        public Pointcut getPointcut() {
            return logPointCut;
        }
    }

    public static class LogPointCut extends StaticMethodMatcherPointcut {

        @SneakyThrows
        @Override
        public boolean matches(Method method, Class<?> aClass) {

            if (method.isAnnotationPresent(AnoDot.class)) {
                return true;
            }

            for (Class in : aClass.getInterfaces()) {
                try {
                    return in.getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(AnoDot.class);
                } catch (Exception e) {
                }
            }
            return false;
        }
    }

    public static class AnoPointCut extends AnnotationMethodMatcher implements Pointcut {

        private ClassFilter classFilter;

        public AnoPointCut() {
            // true 表示支持父类上的注解拦截
            super(AnoDot.class, true);
            this.classFilter = ClassFilter.TRUE;
        }

        public void setClassFilter(ClassFilter classFilter) {
            this.classFilter = classFilter;
        }

        @Override
        public ClassFilter getClassFilter() {
            return this.classFilter;
        }

        @Override
        public final MethodMatcher getMethodMatcher() {
            return this;
        }
    }

    @Bean
    public LogAdvisor init() {
        LogAdvisor logAdvisor = new LogAdvisor();
//        自定义实现姿势
//        logAdvisor.setLogPointCut(new LogPointCut());
        logAdvisor.setLogPointCut(new AnoPointCut());
        logAdvisor.setAdvice(new LogAdvice());
        return logAdvisor;
    }

}
