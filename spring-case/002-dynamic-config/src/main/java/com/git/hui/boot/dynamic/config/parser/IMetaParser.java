package com.git.hui.boot.dynamic.config.parser;

/**
 * Created by @author yihui in 17:12 20/4/22.
 */
public interface IMetaParser<T> {

    T parse(String val);

}
