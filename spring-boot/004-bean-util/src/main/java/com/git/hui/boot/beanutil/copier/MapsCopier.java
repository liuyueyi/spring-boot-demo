package com.git.hui.boot.beanutil.copier;

import com.git.hui.boot.beanutil.copier.mapper.MapStructCopier;
import com.git.hui.boot.beanutil.model.Source;
import com.git.hui.boot.beanutil.model.Target2;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * 缺点是需要定义转换接口；优点是性能最高
 *
 * @author yihui
 * @date 2021/4/8
 */
@Component
public class MapsCopier {
    private MapStructCopier mapStructCopier = Mappers.getMapper(MapStructCopier.class);

    public <K, T> T copy(K source, Class<T> target) {
        return (T) mapStructCopier.copy((Source) source);
    }

    public <T> T copyAndParse(Source source, Class<T> target) {
        return (T) mapStructCopier.copyAndParse(source);
    }
}
