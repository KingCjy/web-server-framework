package com.kingcjy.was.core.web.method.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.Component;
import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.web.PathVariable;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.web.MethodParameter;
import com.kingcjy.was.core.web.context.request.RequestContextHolder;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(PathVariable.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter) {
        RequestMapping requestMapping = methodParameter.getMethod().getAnnotation(RequestMapping.class);
        String uri = getRequestURI(methodParameter.getMethod());

        Map<String, String> pathVariables = getPathVariableMap(uri);

        PathVariable pathVariable = methodParameter.getParameterAnnotation(PathVariable.class);

        Object result = null;
        try {
            result = objectMapper.readValue(pathVariables.get(pathVariable.name()), methodParameter.getParameterType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, String> getPathVariableMap(String uri) {
        String targetUri = RequestContextHolder.getRequest().getRequestURI();

        String[] uriArr = uri.split("/");
        String[] targetUriArr = targetUri.split("/");

        Map<String, String> result = new HashMap<>();

        for(int i = 0; i < uriArr.length; i++) {
            String uriElem = uriArr[i];
            String targetElem = targetUriArr[i];

            if (Pattern.compile("\\{(.*?)}").matcher(uriElem).find() == true) {
                String key = uriElem.replaceAll("\\{", "").replaceAll("}", "");
                result.put(key, targetElem);
            }
        }

        return result;
    }

    private String getRequestURI(Method method) {
        String classRequestUri = getClassURI(method);
        String methodRequestUri = getMethodURI(method);

        return classRequestUri + methodRequestUri;
    }

    private String getClassURI(Method method) {
        RequestMapping requestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }

    private String getMethodURI(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }
}
