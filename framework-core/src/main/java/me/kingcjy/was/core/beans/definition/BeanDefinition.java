package me.kingcjy.was.core.beans.definition;

public interface BeanDefinition {
    Class<?> getBeanClass();
    boolean isAnnotatedDefinition();
}
