package me.kingcjy.was.core.mvc.resolver;

import me.kingcjy.was.core.mvc.method.MethodParameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveArgument(MethodParameter parameter);
}
