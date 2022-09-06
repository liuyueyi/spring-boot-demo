package com.git.hui.web.thymeleaf.rest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YiHui
 * @date 2022/9/6
 */
@Controller
@RequestMapping(path = "page")
public class PageController {

    @Data
    public static class ItemDto {
        private String name;

        private String first;

        private String end;

        private String day;
    }

    public static ItemDto initItem(int size) {
        ItemDto item = new ItemDto();
        item.setName("一灰灰_" + size);
        item.setFirst("f_" + size);
        item.setEnd("e_" + size);
        item.setDay(LocalDate.now().minusDays(size).toString());
        return item;
    }

    @GetMapping(path = "list")
    public String index(Model model) {
        List<ItemDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(initItem(i));
        }
        model.addAttribute("list", list);
        return "page/list";
    }

    @GetMapping(path = "list/items")
    public String items(int page, Model model) {
        List<ItemDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(initItem(page * 5 + i));
        }
        model.addAttribute("list", list);
        return "page/items";
    }

    @Autowired
    private ITemplateEngine templateEngine;

    @ResponseBody
    @GetMapping(path = "list/next")
    public String nextItems(int page) {
        List<ItemDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(initItem(page * 5 + i));
        }

        org.thymeleaf.context.Context context = new Context();
        context.setVariable("list", list);
        String ans = templateEngine.process("page/item-page", context);
        return ans;
    }

}
