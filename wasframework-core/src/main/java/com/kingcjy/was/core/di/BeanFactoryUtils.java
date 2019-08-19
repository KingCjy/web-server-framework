package com.kingcjy.was.core.di;

import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.annotations.Configuration;
import com.kingcjy.was.core.util.AnnotationUtils;
import com.kingcjy.was.core.util.Assert;


public class BeanFactoryUtils {
    private static BeanFactory beanFactory;

    public static BeanFactory getBeanFactory() {
        Assert.notNull(beanFactory, "beanFactory does not initialized");

        return beanFactory;
    }

    static void setBeanFactory(BeanFactory beanFactory) {
        BeanFactoryUtils.beanFactory = beanFactory;
    }

    public static boolean isBean(Class<?> scanClass) {
        boolean hasComponentAnnotation = AnnotationUtils.hasAnotation(scanClass, Component.class);

        if(hasComponentAnnotation == false) {
            return false;
        }

        if(scanClass.isInterface()) {
            return false;
        }

        return true;
    }

    public static boolean isConfigurationClass(Class<?> scanClass) {
        boolean hasConfigurationAnnotation = AnnotationUtils.hasAnotation(scanClass, Configuration.class);

        if(hasConfigurationAnnotation == false) {
            return false;
        }

        if(scanClass.isInterface()) {
            return false;
        }

        return true;
    }
}
