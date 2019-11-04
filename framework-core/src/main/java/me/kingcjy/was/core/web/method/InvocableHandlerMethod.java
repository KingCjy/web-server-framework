package me.kingcjy.was.core.web.method;

import me.kingcjy.was.core.web.method.resolver.HandlerMethodArgumentResolverComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvocableHandlerMethod extends HandlerMethod {

    private static Logger logger = LoggerFactory.getLogger(InvocableHandlerMethod.class);

    private HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();

    public InvocableHandlerMethod(Object instance, Method method) {
        super(instance, method);
    }

    public void setMessageMethodArgumentResolvers(HandlerMethodArgumentResolverComposite argumentResolvers) {
        this.resolvers = argumentResolvers;
    }

    public Object invoke(HttpServletRequest request, Object... providedArguments) {
        Object[] args = getMethodArgumentValues(request, providedArguments);
        try {
            return getMethod().invoke(getInstance(), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request, Object ...providedArgs) {
        MethodParameter[] parameters = getMethodParameters();
        Object[] args = new Object[parameters.length];

        for(int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];

            args[i] = findProvidedArgument(parameter, providedArgs);

            if(args[i] != null) {
                continue;
            }

            if(this.resolvers.supportsParameter(parameter)) {
                args[i] = this.resolvers.resolveArgument(parameter, request);
            }
        }

        return args;
    }

    private static Object findProvidedArgument(MethodParameter parameter, Object ...providedArgs) {
        if(providedArgs == null) {
            return null;
        }

        for (Object providedArg : providedArgs) {
            if(parameter.getParameterType().isInstance(providedArg)) {
                return providedArg;
            }
        }

        return null;
    }
}
