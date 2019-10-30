package me.kingcjy.was.core.mvc.resolver;

import me.kingcjy.was.core.mvc.method.MethodParameter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private Set<HandlerMethodArgumentResolver> argumentResolvers = new HashSet<>();

    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
            new ConcurrentHashMap<>(256);

    public void addHandlerMethodArgumentResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
        argumentResolvers.add(handlerMethodArgumentResolver);
    }

    public void addHandlerMethodArgumentResolver(HandlerMethodArgumentResolver ...handlerMethodArgmentResolvers) {
        argumentResolvers.addAll(Arrays.asList(handlerMethodArgmentResolvers));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter) {
        return getArgumentResolver(parameter);
    }

    private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
        HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
                if (methodArgumentResolver.supportsParameter(parameter)) {
                    result = methodArgumentResolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }
}
