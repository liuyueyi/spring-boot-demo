package com.git.hui.weblfux.path;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by @author yihui in 11:05 20/8/26.
 */
@RestController
@RequestMapping(path = "path")
public class PathAction {

    /**
     * 最基本的path获取方式
     *
     * @param index
     * @return
     */
    @GetMapping(path = "/basic/{index}")
    public Mono<String> basic(@PathVariable(name = "index") int index) {
        return Mono.just("path index: " + index);
    }

    @GetMapping(path = "/basic2/{index}")
    public Mono<String> basic2(@PathVariable(name = "index", required = false) Integer index) {
        return Mono.just("basic2 index: " + index);
    }

    /**
     * 多个参数的场景
     *
     * @param index
     * @param order
     * @return
     */
    @GetMapping(path = "/mbasic/{index}/{order}")
    public Mono<String> mbasic(@PathVariable(name = "index") int index, @PathVariable(name = "order") String order) {
        return Mono.just("mpath arguments: " + index + " | " + order);
    }


    /**
     * 路径中的部分内容匹配
     *
     * - /part/test.txt -> name = test
     * - /part/a/test.txt -> 不匹配
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/part/{name}.txt")
    public Mono<String> part(@PathVariable(name = "name") String name) {
        return Mono.just("part path argument: " + name);
    }


    /**
     * 正则匹配
     *
     * /path/path/pattern/spring-web-3.0.5.jar  -> name = spring-web,  version=3.0.5,  ext=.jar
     *
     * @return
     */
    @GetMapping(path = "/pattern/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    public Mono<String> urlPattern(@PathVariable(name = "name") String name,
            @PathVariable(name = "version") String version, @PathVariable(name = "ext") String ext) {
        return Mono.just("pattern arguments name=" + name + " version=" + version + " ext=" + ext);
    }


    /**
     * 匹配:
     *
     * - /path/pattern2  -> name == ""
     * - /path/pattern2/hello  -> name == /hello
     * - /path/pattern2/test/hello -> name = /test/hello
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/pattern2/{*name}")
    public Mono<String> pattern2(@PathVariable(name = "name") String name) {
        return Mono.just("pattern2 argument: " + name);
    }

    /**
     * 单个*号，只能匹配一级目录，注意这种方式与上面的 pattern2 之间的区别
     *
     * 可以匹配:
     *
     * - /path/pattern3/hello
     * - /path/pattern3
     *
     * 不能匹配
     *
     * - /path/pattern3/hello/1
     *
     * @return
     */
    @GetMapping(path = "/pattern3/*")
    public Mono<String> pattern3() {
        return Mono.just("pattern3 succeed!");
    }

    @GetMapping(path = "/pattern33**")
    public Mono<String> pattern33() {
        return Mono.just("pattern33 succeed!");
    }

    /**
     * 对于 pattern4开头的都可以匹配
     *
     * @return
     */
    @GetMapping(path = "/pattern4/**")
    public Mono<String> pattern4() {
        return Mono.just("pattern4 succeed!");
    }

    /**
     * 匹配  pattern5/test   pattern5/tast ...
     * 不匹配 pattern5/tst pattern5/tesst
     *
     * @return
     */
    @GetMapping(path = "/pattern5/t?st")
    public Mono<String> pattern5() {
        return Mono.just("pattern5 succeed!");
    }
}