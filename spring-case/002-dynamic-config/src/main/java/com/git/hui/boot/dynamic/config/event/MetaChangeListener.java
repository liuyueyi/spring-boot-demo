package com.git.hui.boot.dynamic.config.event;

import com.git.hui.boot.dynamic.config.container.MetaContainer;
import org.springframework.context.ApplicationListener;

/**
 * Created by @author yihui in 21:47 20/4/21.
 */
public class MetaChangeListener implements ApplicationListener<MetaChangeEvent> {
    private MetaContainer metaContainer;

    public MetaChangeListener(MetaContainer metaContainer) {
        this.metaContainer = metaContainer;
    }

    @Override
    public void onApplicationEvent(MetaChangeEvent event) {
        metaContainer.updateMetaVal(event.getKey(), event.getOldVal(), event.getNewVal());
    }
}
