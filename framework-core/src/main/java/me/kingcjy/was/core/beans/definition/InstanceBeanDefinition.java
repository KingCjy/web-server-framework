package me.kingcjy.was.core.beans.definition;

public class InstanceBeanDefinition extends DefaultBeanDefinition {

    private Object instance;

    public InstanceBeanDefinition(Class<?> beanClass, Object instance) {
        super(beanClass);
        this.instance = instance;
    }

    public Object getInstance() {
        return this.instance;
    }

    @Override
    public boolean isInstanceBeanDefinition() {
        return true;
    }

    @Override
    public String toString() {
        return "InstanceBeanDefinition{" +
                "instance=" + instance +
                '}';
    }
}
