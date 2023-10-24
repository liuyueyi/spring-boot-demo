package com.git.hui.id;

import com.git.hui.id.snowflake.HuToolSnowFlakeProducer;
import com.git.hui.id.snowflake.SelfSnowflakeProducer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YiHui
 * @date 2023/10/23
 */
@Controller
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private HuToolSnowFlakeProducer huToolSnowFlakeProducer;
    @Autowired
    private SelfSnowflakeProducer selfSnowflakeProducer;

    @ResponseBody
    @GetMapping(path = "id")
    public String genId() {
        Long hu = huToolSnowFlakeProducer.nextId();
        Long se = selfSnowflakeProducer.nextId();
        return "hu = " + hu + "\n<br/>\nse = " + se;
    }

    @ResponseBody
    @GetMapping(path = "id2")
    public IdVo id2() {
        Long hu = huToolSnowFlakeProducer.nextId();
        Long se = selfSnowflakeProducer.nextId();
        return new IdVo(hu, se);
    }

    @GetMapping("show")
    public String idShow(Model model) {
        Map<String, Long> map = new HashMap<>();
        map.put("hu", huToolSnowFlakeProducer.nextId());
        map.put("se", selfSnowflakeProducer.nextId());
        model.addAllAttributes(map);
        System.out.println("show: " + map);
        return "show";
    }

    @GetMapping("show2")
    public String idShow2(Model model) {
        Long hu = huToolSnowFlakeProducer.nextId();
        Long se = selfSnowflakeProducer.nextId();
        IdVo vo = new IdVo(hu, se);
        model.addAttribute("vo", vo);
        System.out.println("vo: " + vo);
        return "show";
    }

    @Data
    public static class IdVo {
        private Long hu;
        private Long se;
        private String h;
        private String s;

        public IdVo(Long hu, Long se) {
            this.hu = hu;
            this.se = se;
            this.h = String.valueOf(hu);
            this.s = String.valueOf(se);
        }
    }
}
