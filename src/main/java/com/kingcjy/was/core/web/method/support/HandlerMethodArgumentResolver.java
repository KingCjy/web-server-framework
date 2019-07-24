package com.kingcjy.was.core.web.method.support;


import com.kingcjy.was.core.web.MethodParameter;

public interface HandlerMethodArgumentResolver {

    boolean supportsParameter(MethodParameter methodParameter);

    Object resolveArgument(MethodParameter methodParameter);
}
