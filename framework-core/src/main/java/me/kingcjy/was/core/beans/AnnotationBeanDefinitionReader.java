package me.kingcjy.was.core.beans;

import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.beans.definition.AnnotationBeanDefinition;
import me.kingcjy.was.core.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.was.core.utils.MyReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void register(String basePackage) {
        Set<Class> classes = MyReflectionUtils.findAnnotatedClasses(basePackage, Component.class);

        for (Class<?> targetClass : classes) {
            findBeanRegisterMethods(targetClass);
        }
    }

    private void findBeanRegisterMethods(Class<?> targetClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            registerAnnotationBeanDefinition(targetClass, method);
        }
    }

    private void registerAnnotationBeanDefinition(Class<?> targetClass, Method method) {
        if(method.isAnnotationPresent(Bean.class)) {
            beanDefinitionRegistry.registerDefinition(new AnnotationBeanDefinition(targetClass, method));
        }
    }
}
