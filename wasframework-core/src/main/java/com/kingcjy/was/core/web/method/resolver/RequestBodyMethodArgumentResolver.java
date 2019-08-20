package com.kingcjy.was.core.web.method.resolver;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.RequestBody;
import com.kingcjy.was.core.converter.HttpMessageConverter;
import com.kingcjy.was.core.web.MethodParameter;
import com.kingcjy.was.core.web.context.request.RequestContextHolder;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolver;

import java.io.IOException;

@Component
public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper objectMapper;

    private HttpMessageConverter converter;

    public RequestBodyMethodArgumentResolver() {
        converter = new HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RequestBody.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();

        Object returnArgument = null;
        try {
            returnArgument = converter.readBody(RequestContextHolder.getRequest().getInputStream(), parameterType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnArgument;
    }
}
