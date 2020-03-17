package com.git.hui.boot.client.load;

import lombok.Getter;
import lombok.ToString;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 11:42 20/2/28.
 */
@ToString
public class DatasourceLoader {

    @Getter
    private String mode;

    public DatasourceLoader(Environment environment) {
        this.mode = environment.getProperty("config.save.mode");
        System.out.println("init DatasourceLoader for:" + mode);
    }

    @PostConstruct
    public void loadResourcres() {
        System.out.println("开始初始化资源");
    }
}
