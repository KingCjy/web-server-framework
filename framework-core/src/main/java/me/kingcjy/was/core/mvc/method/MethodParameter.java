package me.kingcjy.was.core.mvc.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodParameter {

    private static final Logger logger = LoggerFactory.getLogger(MethodParameter.class);

    private Method method;
    private int parameterIndex;

    private Class<?> parameterType;

    public MethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameterIndex = parameterIndex;

        this.parameterType = method.getParameterTypes()[this.parameterIndex];
    }

    public <T> T getParameterAnnotation(Class<T> targetAnnotation) {
        for (Annotation annotation : method.getParameters()[this.parameterIndex].getAnnotations()) {
            if(annotation.annotationType().equals(targetAnnotation)) {
                return (T) annotation;
            }
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }
}
