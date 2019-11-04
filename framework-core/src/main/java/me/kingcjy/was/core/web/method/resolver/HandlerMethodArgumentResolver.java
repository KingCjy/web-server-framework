package me.kingcjy.was.core.web.method.resolver;

import me.kingcjy.was.core.web.method.MethodParameter;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveArgument(MethodParameter parameter, HttpServletRequest request);
}
