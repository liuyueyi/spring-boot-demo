package com.git.hui.boot.spi.egine;

import com.git.hui.boot.spi.exception.NoSpiChooseException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 16:13 18/10/23.
 */
public class SpiFactoryBean<T> implements FactoryBean<T> {
    private ApplicationContext applicationContext;
    private Class<? extends ISpi> spiClz;

    private List<ISpi> list;

    public SpiFactoryBean(ApplicationContext applicationContext, Class<? extends ISpi> clz) {
        this.applicationContext = applicationContext;
        this.spiClz = clz;

        Map<String, ? extends ISpi> map = applicationContext.getBeansOfType(spiClz);
        list = new ArrayList<>(map.values());
        list.sort(Comparator.comparingInt(ISpi::order));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {


        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                for (ISpi spi : list) {
                    if (spi.verify(args[0])) {
                        // 第一个参数作为条件选择
                        return method.invoke(spi, args);
                    }
                }

                throw new NoSpiChooseException("no spi server can execute! spiList: " + list);
            }
        };

        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{spiClz},
                invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return spiClz;
    }
}
