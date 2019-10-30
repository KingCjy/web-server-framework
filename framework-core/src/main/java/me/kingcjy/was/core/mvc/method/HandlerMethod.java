package me.kingcjy.was.core.mvc.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class HandlerMethod {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethod.class);

    private Method method;
    private Object instance;

    private MethodParameter[] parameters;

    public HandlerMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;

        parameters = initMethodParameters();
    }

    private MethodParameter[] initMethodParameters() {
        int count = this.method.getParameterCount();

        MethodParameter[] result = new MethodParameter[count];

        for(int i = 0; i < count; i++) {
            MethodParameter methodParameter = new MethodParameter(this.method, i);
            result[i] = methodParameter;
        }
        return result;
    }

    public Object invoke(Object ... arguments) throws Exception {
        return method.invoke(instance, arguments);
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }
}
