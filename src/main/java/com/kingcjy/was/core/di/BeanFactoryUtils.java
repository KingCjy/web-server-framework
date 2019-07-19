package com.kingcjy.was.core.di;

import com.google.common.collect.Sets;
import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.Service;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class BeanFactoryUtils {

    private static BeanFactory beanFactory;

    public static BeanFactory initBeanFactory(String basePackage) {
        Reflections reflections = new Reflections(basePackage, "com.kingcjy.was.core.configuration");
        Set<Class<?>> annotatedClassList = getTypesAnnotatedWith(reflections, Component.class, Controller.class, Service.class);

        beanFactory = new BeanFactory(annotatedClassList);
        return beanFactory;
    }

    public static BeanFactory getBeanFactory() {
        if(beanFactory == null) {
            throw new RuntimeException("BeanFactory does not initialized");
        }
        return beanFactory;
    }

    private static Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> beanList = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beanList.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beanList;
    }
}
