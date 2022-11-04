package com.git.hui.extention.postprocessor.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringValueResolver;

/**
 * 通过重写PropertySourcesPlaceholderConfigurer, 来实现加密的配置参数解密处理
 *
 * @author YiHui
 * @date 2022/11/4
 */
@Configuration
public class DecryptPropertSourcePlaceHolderConfigurer extends PropertySourcesPlaceholderConfigurer {
    private static final String ENC_SYMBOL = "encrypt@";

    /**
     * 注意，重写这个方法并没有效果，因为在PropertySourcesPlaceholderConfigurer实现中，它不会被调用；
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    protected String convertProperty(String propertyName, String propertyValue) {
        return this.convertPropertyValue(propertyValue);
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
        propertyResolver.setValueSeparator(this.valueSeparator);
        StringValueResolver valueResolver = (strVal) -> {
            String resolved = this.ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal) : propertyResolver.resolveRequiredPlaceholders(strVal);
            if (this.trimValues) {
                resolved = resolved.trim();
            }

            if (resolved.equals(this.nullValue)) {
                return null;
            }

            if (resolved.startsWith(ENC_SYMBOL)) {
                // 表示属性为加密存储的，需要进行解密，这里简单起见，直接使用base64进行解密
                resolved = new String(Base64Utils.decodeFromString(resolved.substring(ENC_SYMBOL.length())));
            }
            return resolved;
        };
        this.doProcessProperties(beanFactoryToProcess, valueResolver);
    }

}
