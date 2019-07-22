package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class RequestMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

    private Map<MethodInfo, Method> mappingList = new HashMap<>();

    void initMapping(Collection<Method> methodList) {
        logger.info("Initialized Request Mapping");
        methodList.forEach(method -> {
            MethodInfo methodInfo = getMethodInfo(method);
            mappingList.put(methodInfo, method);
            logger.info("URI : [{}] [{}], Class : [{}], Method : {} mapped", methodInfo.getRequestMethod().name(), methodInfo.getUri() , method.getDeclaringClass().getName(),  method.getName());
        });
    }

    public Method findMethod(String uri, RequestMethod method) {
        return mappingList.get(new MethodInfo(uri, method));
    }

    private MethodInfo getMethodInfo(Method method) {
        String classRequestUri = getClassRequestUri(method);
        String methodRequestUri = getMethodRequestUri(method);

        RequestMethod requestMethod = getRequestMethod(method);

        return new MethodInfo(classRequestUri + methodRequestUri, requestMethod);
    }

    private String getClassRequestUri(Method method) {
        RequestMapping requestMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }

    private String getMethodRequestUri(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }

    private RequestMethod getRequestMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return RequestMethod.GET;
        }

        return requestMapping.method();
    }
}
