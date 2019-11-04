package me.kingcjy.was.core.web.method.resolver;

import me.kingcjy.was.core.web.method.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private Set<HandlerMethodArgumentResolver> argumentResolvers = new HashSet<>();

    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
            new ConcurrentHashMap<>(256);

    public void addResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
        argumentResolvers.add(handlerMethodArgumentResolver);
    }

    public void addResolver(HandlerMethodArgumentResolver ...handlerMethodArgmentResolvers) {
        argumentResolvers.addAll(Arrays.asList(handlerMethodArgmentResolvers));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) {
        return getArgumentResolver(parameter).resolveArgument(parameter, request);
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
