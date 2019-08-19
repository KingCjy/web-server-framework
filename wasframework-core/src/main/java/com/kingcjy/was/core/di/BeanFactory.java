package com.kingcjy.was.core.di;

public interface BeanFactory {
    Object getBean(String name);

    <T> T getBean(String name, Class<?> requiredType);

    void addBean(String name, Class<?> type, Object instance);

    Object[] getBeans();
}
