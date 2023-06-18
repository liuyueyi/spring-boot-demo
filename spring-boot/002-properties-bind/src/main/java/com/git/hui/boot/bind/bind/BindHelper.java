package com.git.hui.boot.bind.bind;

import lombok.Data;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 手动实现配置加载配置类的方式
 *
 * @author YiHui
 * @date 2023/5/12
 */
@Component
public class BindHelper implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void bindInfo() {
        // 直接将前缀对应的配置，加载到指定的对象中
        Binder binder = Binder.get(environment);


        // 直接绑定到配置类
        Mail mail = binder.bindOrCreate("demo.mail", Mail.class);
        System.out.println("mail = " + mail);

        mail = binder.bindOrCreate("demo.mail2", Mail.class);
        System.out.println("mail = " + mail);

        try {
            mail = binder.bind("demo.mail2", Mail.class).get();
            System.out.println("mail = " + mail);
        } catch (Exception e) {
            // 因为配置不存在，会报错
            System.out.println(e.getMessage());
        }

        // 将配置绑定到Map中
        Map<String, DsConfig> dsMap = binder.bind("demo.dynamic", Bindable.mapOf(String.class, DsConfig.class)).get();
        System.out.println("Map Config: " + dsMap);

        // 将配置绑定到list
        List<Proxy> proxyList = binder.bindOrCreate("demo.proxy", Bindable.listOf(Proxy.class));
        System.out.println("list config: " + proxyList);

        // 对配置进行解析
        String decPwd = binder.bind("demo.enc.pwd", Bindable.of(String.class))
                .map(s -> new String(Base64Utils.decodeFromString(s))).get();
        System.out.println("解码之后的数据是: " + decPwd);


        // 注册绑定过程回调
        String dec = binder.bindOrCreate("demo.enc.pwd", Bindable.of(String.class), new BindHandler() {
            @Override
            public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
                System.out.println("开始绑定: " + name);
                return BindHandler.super.onStart(name, target, context);
            }

            @Override
            public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
                System.out.println("绑定成功!" + name + " val:" + target.getValue() + " res: " + result);
                return new String(Base64Utils.decodeFromString((String) result));
            }

            @Override
            public Object onCreate(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
                System.out.println("创建: " + name + " val:" + target.getValue() + " res: " + result);
                return BindHandler.super.onCreate(name, target, context, result);
            }

            @Override
            public Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error) throws Exception {
                System.out.println("绑定失败! " + name + "  " + error.getMessage());
                return BindHandler.super.onFailure(name, target, context, error);
            }

            @Override
            public void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) throws Exception {
                System.out.println("绑定结束: " + name + " val:" + target.getValue() + " res: " + result);
                BindHandler.super.onFinish(name, target, context, result);
            }
        });
        System.out.println("绑定回调： " + dec);
        System.out.println("---------------");
    }


    @Data
    public static class DsConfig {
        private String user;
        private String password;
    }

    @Data
    public static class Proxy {
        private String ip;
        private Integer port;
    }

    @Data
    public static class Mail {
        private String host;
        private String port;
        private String user;
        private String password;
        private String from;
    }
}
