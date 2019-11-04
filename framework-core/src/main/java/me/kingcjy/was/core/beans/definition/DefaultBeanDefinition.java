package me.kingcjy.was.core.beans.definition;

public class DefaultBeanDefinition implements BeanDefinition {

    private String name;
    private Class<?> beanClass;

    public DefaultBeanDefinition(Class<?> beanClass) {
        this(beanClass, beanClass.getName());
    }

    public DefaultBeanDefinition(Class<?> beanClass, String name) {
        this.beanClass = beanClass;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
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
