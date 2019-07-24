package com.kingcjy.was.core.web.method;

import com.kingcjy.was.core.web.MethodParameter;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolverComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class HandlerMethod {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethod.class);

    private Method method;
    private Object instance;

    private MethodParameter[] parameters;

    private HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();

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

    public Object invoke() throws Exception {
        return method.invoke(instance, getMethodArgumentValues());
    }

    private Object[] getMethodArgumentValues() {

        Object[] args = new Object[this.parameters.length];

        for(int i = 0; i < this.parameters.length; i++) {
            MethodParameter parameter = this.parameters[i];

           if(this.resolvers.supportsParameter(parameter)) {
                args[i] = this.resolvers.resolveArgument(parameter);
           }
        }

        return args;
    }

    public void setResolvers(HandlerMethodArgumentResolverComposite resolvers) {
        this.resolvers = resolvers;
    }
}
