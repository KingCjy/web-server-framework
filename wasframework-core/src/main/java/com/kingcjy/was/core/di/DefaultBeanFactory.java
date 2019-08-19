package com.kingcjy.was.core.di;

import com.kingcjy.was.core.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {

    private Map<String, Object> beans = new HashMap<>();

    DefaultBeanFactory() {}

    @Override
    public Object getBean(String name) {
        Object bean = beans.get(name);
        Assert.notNull(bean, "bean name " + name + " is not registered");

        return beans.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<?> requiredType) {
        Object bean = beans.get(name);
        Assert.notNull(bean, "bean name " + name + " is not registered");

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
}
