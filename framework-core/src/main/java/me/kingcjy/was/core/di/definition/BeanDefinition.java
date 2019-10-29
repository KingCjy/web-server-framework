package me.kingcjy.was.core.di.definition;

public interface BeanDefinition {
    Class<?> getBeanClass();
    boolean isAnnotatedDefinition();
}
