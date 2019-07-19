package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class RequestMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

    private Map<String, Method> mappingList = new HashMap<>();

    void initMapping(Collection<Method> methodList) {
        logger.info("Initialized Request Mapping");
        methodList.forEach(method -> {
            String uri = getRequestUri(method);
            mappingList.put(uri, method);
            logger.info("URI : [{}], Class : [{}], Method : {} mapped", uri, method.getDeclaringClass().getName(),  method.getName());
        });
    }

    public Method findMethod(String uri) {
        return mappingList.get(uri);
    }

    private String getRequestUri(Method method) {
        String classRequestUri = getClassRequestUri(method);
        String methodRequestUri = getMethodRequestUri(method);

        return classRequestUri + methodRequestUri;
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
}
