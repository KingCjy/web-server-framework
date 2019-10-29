package me.kingcjy.was.core.di.definition;

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
}
