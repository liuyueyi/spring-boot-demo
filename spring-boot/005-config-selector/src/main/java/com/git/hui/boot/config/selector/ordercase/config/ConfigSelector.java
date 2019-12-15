package com.git.hui.boot.config.selector.ordercase.config;

import com.git.hui.boot.config.selector.ordercase.ano.DemoSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by @author yihui in 18:18 19/12/13.
 */
public class ConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        System.out.println("select import");
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DemoSelector.class.getName()));

        String config = attributes.getString("value");
        if ("config1".equalsIgnoreCase(config)) {
            return new String[]{ToSelectorAutoConfig1.class.getName()};
        } else if ("config2".equalsIgnoreCase(config)) {
            return new String[]{ToSelectorAutoConfig2.class.getName()};
        } else {
            return new String[]{ToSelectorAutoConfig1.class.getName(), ToSelectorAutoConfig2.class.getName()};
        }
    }
}
