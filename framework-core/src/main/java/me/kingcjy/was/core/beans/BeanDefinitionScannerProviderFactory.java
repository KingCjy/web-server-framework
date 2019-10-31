package me.kingcjy.was.core.beans;

import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.beans.provider.BeanDefinitionScannerProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

public class BeanDefinitionScannerProviderFactory {
    public Set<BeanDefinitionScannerProvider> getBeanDefinitionScannerProviders(BeanDefinitionRegistry beanDefinitionRegistry) {
        ServiceLoader<BeanDefinitionScannerProvider> serviceLoader = ServiceLoader.load(BeanDefinitionScannerProvider.class);

        Set<BeanDefinitionScannerProvider> beanDefinitionScannerProviders = new HashSet<>();
        serviceLoader.forEach(beanDefinitionScannerProvider -> {
            beanDefinitionScannerProvider.setBeanDefinitionRegistry(beanDefinitionRegistry);
            beanDefinitionScannerProviders.add(beanDefinitionScannerProvider);
        });

        return beanDefinitionScannerProviders;
    }
}
