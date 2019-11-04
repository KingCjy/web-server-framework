package me.kingcjy.was.core.mvc;

import me.kingcjy.was.core.annotations.web.RequestMapping;
import me.kingcjy.was.core.annotations.web.RequestMethod;
import me.kingcjy.was.core.annotations.web.RestController;
import me.kingcjy.was.core.beans.factory.BeanFactory;
import me.kingcjy.was.core.beans.factory.BeanFactoryAware;
import me.kingcjy.was.core.web.method.InvocableHandlerMethod;
import me.kingcjy.was.core.web.method.resolver.DefaultHandlerMethodFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private BeanFactory beanFactory;
    private Map<HandlerKey, InvocableHandlerMethod> handlers = new HashMap<>();


    private DefaultHandlerMethodFactory handlerMethodFactory;

    public AnnotationHandlerMapping(BeanFactory beanFactory, DefaultHandlerMethodFactory defaultHandlerMethodFactory) {
        this.beanFactory = beanFactory;
        this.handlerMethodFactory = defaultHandlerMethodFactory;

        initialize();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void initialize() {
        Map<Class<?>, Object> controllers = findAllControllers();
        Set<Method> requestMappingMethods = findAllRequestMappingMethods(controllers.keySet());

        putMethodsToHandler(controllers, requestMappingMethods);
    }

    private void putMethodsToHandler(Map<Class<?>, Object> controllers, Set<Method> requestMappingMethods) {
        for (Method method : requestMappingMethods) {
            HandlerKey handlerKey = createHandlerKey(method);

            InvocableHandlerMethod handlerMethod = handlerMethodFactory.createInvocableHandlerMethod(controllers.get(method.getDeclaringClass()), method);
            handlers.put(handlerKey, handlerMethod);
            logger.debug("register RequestHandler uri : {}, method: {}", handlerKey.getUri(), method);
        }
    }

    private HandlerKey createHandlerKey(Method method) {
        String classUri = findClassUri(method.getDeclaringClass());
        String methodUri = findMethodUri(method);

        RequestMethod requestMethod = findRequestMethod(method);

        return new HandlerKey(classUri + methodUri, requestMethod);
    }

    private String findClassUri(Class<?> targetClass) {
        if(targetClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = targetClass.getAnnotation(RequestMapping.class);
            return requestMapping.value();
        }
        return "";
    }

    private String findMethodUri(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        return requestMapping.value();
    }

    private RequestMethod findRequestMethod(Method method) {
        return method.getAnnotation(RequestMapping.class).method();
    }

    private Map<Class<?>, Object> findAllControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        for (Object bean : this.beanFactory.getBeans()) {
            if(bean.getClass().isAnnotationPresent(RestController.class)) {
                controllers.put(bean.getClass(), this.beanFactory.getBean(bean.getClass().getName()));
            }
        }
        return controllers;
    }

    private Set<Method> findAllRequestMappingMethods(Set<Class<?>> controllers) {
        Set<Method> requestMappingMethods = new HashSet<>();
        for (Class<?> controller : controllers) {
            requestMappingMethods.addAll(findRequestMappingMethods(controller));
        }
        return requestMappingMethods;
    }

    private Set<Method> findRequestMappingMethods(Class<?> controller) {
        Set<Method> requestMappingMethods = new HashSet<>();
        for (Method method : controller.getDeclaredMethods()) {
            if(method.isAnnotationPresent(RequestMapping.class)) {
                requestMappingMethods.add(method);
            }
        }
        return requestMappingMethods;
    }

    @Override
    public InvocableHandlerMethod getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());
        Optional<HandlerKey> handlerKeyOptional = handlers.keySet().stream()
                .filter(handler -> handler.matches(uri, requestMethod))
                .findAny();
        if(handlerKeyOptional.isPresent() == false) {
            return null;
        }
        return handlers.get(handlerKeyOptional.get());
    }
}
