package com.kingcjy.was.core.di;

import com.google.common.collect.Maps;
import com.kingcjy.was.core.annotations.Bean;
import com.kingcjy.was.core.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Map<Class<?>, Object> beanList = Maps.newHashMap();

    BeanFactory(Map<Class<?>, Object> beans, Set<Class<?>> classList) {
        beanList.putAll(beans);
        initializeBeans(classList);
        injectFields();
    }

    private void initializeBeans(Set<Class<?>> classList) {
        for (Class<?> clazz : classList) {
            try {
                Class<?> tt = BeanFactory.class.getClassLoader().loadClass(clazz.getName());
                registerBean(tt, tt.newInstance());
                logger.info(clazz.getName() + " bean registered");
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void injectFields() {
        for(Class<?> clazz : beanList.keySet()) {
            for(Field field : clazz.getDeclaredFields()) {
                if(field.getAnnotation(Inject.class) != null) {
                    field.setAccessible(true);
                    try {
                        field.set(beanList.get(clazz), beanList.get(field.getType()));
                        logger.info(clazz.getName() + " bean " + field.getName() + " fields injected");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void registerBean(Class<?> clazz, Object object) {
        if(beanList.containsKey(clazz)) {
            logger.error(clazz.getName() + " is already registered");
            return;
        }

        beanList.put(clazz, object);
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beanList.get(clazz);
    }

    public Collection<Object> getAllBeanList() {
        return beanList.values();
    }

    public Collection<Class<?>> getAllKeyList() {
        return beanList.keySet();
    }

    public Collection<Class<?>> getTypedAnnotatedWith(Class<? extends Annotation> annotation) {
        List<Class<?>> list = new ArrayList<>();

        for (Class<?> key : beanList.keySet()) {
            if(key.getAnnotation(annotation) != null) {
                list.add(key);
            }
        }
        return list;
    }

    public Collection<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        List<Method> list = new ArrayList<>();

        for (Class<?> clazz : beanList.keySet()) {
            for(Method method : clazz.getMethods()) {
                if(method.getAnnotation(annotation) != null) {
                    list.add(method);
                }
            }
        }
        return list;
    }

    public void registerConfigurationClass(Set<Class<?>> configurationClass) {
        for(Class<?> clazz : configurationClass) {

            for(Method method : clazz.getDeclaredMethods()) {
                if(method.getAnnotation(Bean.class) != null) {
//                    Object object =
                }
            }
        }
    }
}
