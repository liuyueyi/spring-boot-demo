package com.git.hui.boot.web.rest;

import com.git.hui.boot.web.dynamic.DynamicObjLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 14:08 19/3/29.
 */
@RestController
public class HelloRest {

    String code = "package com.git.hui.boot.web.rest;\n" + "\n" + "import com.alibaba.fastjson.JSON;\n" + "\n" +
            "import java.util.HashMap;\n" + "import java.util.Map;\n" + "\n" + "/**\n" +
            " * Created by @author yihui in 14:09 19/3/29.\n" + " */\n" + "public class DemoBean {\n" + "\n" +
            "    public String show() {\n" + "        Map<String, String> map = new HashMap<>();\n" +
            "        map.put(\"hello\", \"world\");\n" + "        return JSON.toJSONString(map);\n" + "    }\n" + "\n" +
            "}\n";

    @GetMapping(path = {"/", "", "/index"})
    public String show() {
        Object obj = DynamicObjLoader.loadTask(code);
        return obj != null ? "true" : "false";
    }

    @GetMapping(path = "/hello")
    public String hello() {
        return "hello";
    }
}
