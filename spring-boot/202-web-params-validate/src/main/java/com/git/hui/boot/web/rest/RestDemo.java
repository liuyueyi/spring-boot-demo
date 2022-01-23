package com.git.hui.boot.web.rest;

import com.git.hui.boot.web.model.ReqDo;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by @author yihui in 11:41 20/5/25.
 */
@RestController
public class RestDemo {

    @GetMapping(path = "exception")
    public String exception(@Valid ReqDo reqDo) {
        return reqDo.toString();
    }

    @GetMapping(path = "bind")
    public String bind(@Valid ReqDo reqDo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()).toString();
        }

        return reqDo.toString();
    }


    @GetMapping(path = "manual")
    public String manual(ReqDo reqDo) {
        Set<ConstraintViolation<ReqDo>> ans =
                Validation.buildDefaultValidatorFactory().getValidator().validate(reqDo, new Class[0]);
        if (!CollectionUtils.isEmpty(ans)) {
            return ans.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).toString();
        }

        return reqDo.toString();
    }

    @PostMapping(path = "post")
    public ReqDo post(@RequestBody ReqDo reqDo) {
        System.out.println(reqDo);
        return reqDo;
    }

    /**
     * curl 'http://127.0.0.1:8080/check' -X POST -H 'content-type:application/json' -d '{"age": 18, "name": "一灰灰", "email": "xx@xx.com", "id_card": "4209120123123432", "xx": 12}'
     *
     * @param req
     * @return
     */
    @PostMapping(path = "check")
    public ReqDo check(@RequestBody ReqDo req) {
        System.out.println("req: " + req);
        return req;
    }

    @GetMapping(path = "get")
    public ReqDo get(ReqDo req) {
        System.out.println(req);
        return req;
    }
}
