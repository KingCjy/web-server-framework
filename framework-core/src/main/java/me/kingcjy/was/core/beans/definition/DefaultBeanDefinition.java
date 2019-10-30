package me.kingcjy.was.core.beans.definition;

public class DefaultBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    public DefaultBeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public boolean isAnnotatedDefinition() {
        return false;
    }

    @Override
    public String toString() {
        return "DefaultBeanDefinition{" +
                "beanClass=" + beanClass +
                '}';
    }
}
