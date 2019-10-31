package me.kingcjy.was.data.core;

import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.beans.provider.BeanDefinitionScannerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryComponentProvider implements BeanDefinitionScannerProvider {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryComponentProvider.class);

    private BeanDefinitionRegistry registry;

    @Override
    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.registry = registry;
    }

    @Override
    public void scan(String basePackage) {
        logger.debug(getClass().getName() + " scanned");
    }
}
