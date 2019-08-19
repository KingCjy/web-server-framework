package com.kingcjy.was.core.web.method.support;

import com.kingcjy.was.core.web.MethodParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolverComposite.class);

    private final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList();

    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
            new ConcurrentHashMap<>(256);

    public HandlerMethodArgumentResolverComposite addResolver(HandlerMethodArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
        return this;
    }

    public HandlerMethodArgumentResolverComposite addResolver(HandlerMethodArgumentResolver...resolvers) {
        Collections.addAll(this.argumentResolvers, resolvers);
        return this;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }


    @Override
    public Object resolveArgument(MethodParameter methodParameter) {
        HandlerMethodArgumentResolver resolver = getArgumentResolver(methodParameter);
        if (resolver == null) {
            throw new IllegalArgumentException(
                    "Unsupported parameter type [" + methodParameter.getParameterType().getName() + "]." +
                            " supportsParameter should be called first.");
        }
        return resolver.resolveArgument(methodParameter);
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
