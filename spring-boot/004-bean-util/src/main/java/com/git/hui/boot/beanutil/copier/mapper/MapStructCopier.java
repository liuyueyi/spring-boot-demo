package com.git.hui.boot.beanutil.copier.mapper;

import com.git.hui.boot.beanutil.model.Source;
import com.git.hui.boot.beanutil.model.Target;
import com.git.hui.boot.beanutil.model.Target2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author yihui
 * @date 2021/4/8
 */
@Mapper
public interface MapStructCopier {
    @Mappings({
            @Mapping(target = "userName", source = "user_name"),
            @Mapping(target = "market_price", source = "marketPrice")
    })
    Target2 copyAndParse(Source source);

    Target copy(Source source);

}
