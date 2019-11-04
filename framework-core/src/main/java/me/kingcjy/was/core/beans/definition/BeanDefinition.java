package me.kingcjy.was.core.beans.definition;

public interface BeanDefinition {

    String getName();
    Class<?> getBeanClass();
    default boolean isAnnotatedDefinition() {
        return false;
    }

    default boolean isInstanceBeanDefinition() {
        return false;
    }
}
