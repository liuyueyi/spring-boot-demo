package com.git.hui.boo.webclient.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by @author yihui in 21:40 20/7/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Body implements Serializable {
    private static final long serialVersionUID = 1210673970258821332L;
    String name;
    Integer age;
}