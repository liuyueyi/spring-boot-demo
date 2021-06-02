package com.git.hui.boot.properties.value.parse;

import com.git.hui.boot.properties.value.model.Jwt;

import java.beans.PropertyEditorSupport;

/**
 * 当Editor 与 PODO 放在同一个包路径下时，不需要主动注册，就会被Spring识别到
 *
 * @author yihui
 * @date 2021/6/2
 */
public class JwtEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Jwt.parse(text, "JwtEditor"));
    }
}
