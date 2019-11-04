package me.kingcjy.was.core.beans.definition;

import java.lang.reflect.Method;

public class AnnotationBeanDefinition extends DefaultBeanDefinition {

    private Method method;

    public AnnotationBeanDefinition(Class<?> targetClass, Method method) {
        super(targetClass);
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    @Override
    public boolean isAnnotatedDefinition() {
        return true;
    }

    @Override
    public Class<?> getBeanClass() {
        return method.getReturnType();
    }

    @Override
    public String toString() {
        return "AnnotationBeanDefinition{" +
                "method=" + method +
                '}';
    }
}
