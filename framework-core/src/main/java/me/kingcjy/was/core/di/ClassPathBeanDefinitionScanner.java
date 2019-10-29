package me.kingcjy.was.core.di;

import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.di.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.di.definition.DefaultBeanDefinition;
import me.kingcjy.was.core.utils.MyReflectionUtils;

import java.util.Set;

public class ClassPathBeanDefinitionScanner {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void scan(String basePackage) {
        Set<Class> classes = MyReflectionUtils.findAnnotatedClasses(basePackage, Component.class);

        for (Class targetClass : classes) {
            beanDefinitionRegistry.registerDefinition(new DefaultBeanDefinition(targetClass));
        }
    }
}
