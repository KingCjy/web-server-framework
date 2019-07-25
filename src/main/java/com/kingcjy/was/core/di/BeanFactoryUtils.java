package com.kingcjy.was.core.di;

import com.google.common.collect.Sets;
import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.annotations.Configuration;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.Service;

import com.kingcjy.was.core.util.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public class BeanFactoryUtils {

    private static BeanFactory beanFactory;

    public static BeanFactory initBeanFactory(Map<Class<?>, Object> beans) {
        Set<Class<?>> annotatedClassList = ReflectionUtils.getTypesAnnotatedWith(Component.class, Controller.class, Service.class);
        Set<Class<?>> configurationClass = ReflectionUtils.getTypesAnnotatedWith(Configuration.class);


        beanFactory = new BeanFactory(beans, annotatedClassList);
        beanFactory.registerConfigurationClass(configurationClass);
        return beanFactory;
    }

    public static BeanFactory getBeanFactory() {
        if(beanFactory == null) {
            throw new RuntimeException("BeanFactory does not initialized");
        }
        return beanFactory;
    }
}
