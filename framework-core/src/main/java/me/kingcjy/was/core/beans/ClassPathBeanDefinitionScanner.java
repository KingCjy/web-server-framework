package me.kingcjy.was.core.beans;

import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.beans.definition.DefaultBeanDefinition;
import me.kingcjy.was.core.beans.provider.BeanDefinitionScannerProvider;
import me.kingcjy.was.core.utils.MyReflectionUtils;

import java.util.Set;

public class ClassPathBeanDefinitionScanner implements BeanDefinitionScannerProvider {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void scan(String basePackage) {
        Set<Class> classes = MyReflectionUtils.findAnnotatedClasses(basePackage, Component.class);

        for (Class targetClass : classes) {
            Component component = (Component) targetClass.getAnnotation(Component.class);

            if(component != null && component.value().isEmpty() == false) {
                beanDefinitionRegistry.registerDefinition(new DefaultBeanDefinition(targetClass, component.value()));
            } else {
                beanDefinitionRegistry.registerDefinition(new DefaultBeanDefinition(targetClass));
            }
        }
    }
}
