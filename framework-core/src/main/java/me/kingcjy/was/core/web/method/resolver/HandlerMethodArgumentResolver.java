package me.kingcjy.was.core.web.method.resolver;

import me.kingcjy.was.core.web.method.MethodParameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveArgument(MethodParameter parameter);
}
