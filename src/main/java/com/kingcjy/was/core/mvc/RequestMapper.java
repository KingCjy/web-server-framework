package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.util.ClassUtils;
import com.kingcjy.was.core.web.method.HandlerMethod;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolver;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolverComposite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

public class RequestMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

    private Map<MethodInfo, Method> mappingList = new HashMap<>();

    private HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;

    void initMapping(Collection<Method> methodList) {
        logger.info("Initialized Request Mapping");
        methodList.forEach(method -> {
            MethodInfo methodInfo = getMethodInfo(method);
            mappingList.put(methodInfo, method);
            logger.info("URI : [{}] [{}], Class : [{}], Method : {} mapped", methodInfo.getRequestMethod().name(), methodInfo.getUri() , method.getDeclaringClass().getName(),  method.getName());
        });

        Class<?>[] resolverClasses = ClassUtils.isAssignableFrom(HandlerMethodArgumentResolver.class);

        List<Object> resolvers = new ArrayList<>();

        for (Class<?> resolverClass : resolverClasses) {
            Object object = BeanFactoryUtils.getBeanFactory().getBean(resolverClass);
            if(object == null) {
                continue;
            }
            resolvers.add(object);
        }

        handlerMethodArgumentResolverComposite = new HandlerMethodArgumentResolverComposite();
        handlerMethodArgumentResolverComposite.addResolver(resolvers.toArray(new HandlerMethodArgumentResolver[]{}));
    }


    public String onRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        Method method = findMethod(requestURI, requestMethod);
        Object instance = BeanFactoryUtils.getBeanFactory().getBean(method.getDeclaringClass());
        HandlerMethod handlerMethod = new HandlerMethod(instance, method);
        handlerMethod.setResolvers(handlerMethodArgumentResolverComposite);

        return new ObjectMapper().writeValueAsString(handlerMethod.invoke(request, response));
    }

    private Method findMethod(String uri, RequestMethod method) {
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
