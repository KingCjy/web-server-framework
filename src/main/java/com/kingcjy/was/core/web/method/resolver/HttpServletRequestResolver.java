package com.kingcjy.was.core.web.method.resolver;

import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.web.MethodParameter;
import com.kingcjy.was.core.web.context.request.RequestContextHolder;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

@Component
public class HttpServletRequestResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(HttpServletRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter) {
        return RequestContextHolder.getRequest();
    }
}
