package me.kingcjy.was.core.di;

public interface BeanFactory {
    Object getBean(String name);
    <T> T getBean(String name, Class<?> type);

    Object[] getBeans();
}
