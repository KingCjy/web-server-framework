package com.kingcjy.was.core.di;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    DefaultBeanFactory() {}

    @Override
    public Object getBean(String name) {
        Object bean = beans.get(name);

        return beans.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<?> requiredType) {
        Object bean = beans.get(name);

        return (T) beans.get(name);
    }

    @Override
    public void addBean(String name, Class<?> type, Object instance) {
        beans.put(name, instance);
    }

    @Override
    public Object[] getBeans() {
        return beans.values().toArray(new Object[]{});
    }

    @Override
    public void injectFields() {
        for (Object bean : getBeans()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if(field.getAnnotation(Autowired.class) != null) {
                    field.setAccessible(true);
                    try {
                        field.set(bean, getBean(field.getType().getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
