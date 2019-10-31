package me.kingcjy.was.core.beans.provider;

import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;

public interface BeanDefinitionScannerProvider {
    void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry);
    void scan(String basePackage);
}
