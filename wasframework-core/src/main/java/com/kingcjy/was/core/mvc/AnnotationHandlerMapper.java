package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.web.method.HandlerMethod;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolver;
import com.kingcjy.was.core.web.method.support.HandlerMethodArgumentResolverComposite;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapper {

    private final static Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapper.class);

    private  Map<URIInfo, HandlerMethod> handlerMethods = new HashMap<>();

    private HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;

    public AnnotationHandlerMapper() {
        String basePackage = System.getProperty("was.basePackage");

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(new ClassLoader[] { ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader() }))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage))));

        Set<Method> annotatedMethod = findAnnotatedMethods(reflections);

        initializeHandlerMethodArgumentResolverComposite();

        for (Method method : annotatedMethod) {
            URIInfo uriInfo = createUriInfo(method);
            Object instance = BeanFactoryUtils.getBeanFactory().getBean(method.getDeclaringClass().getName());

            HandlerMethod handlerMethod = new HandlerMethod(instance, method);
            handlerMethod.setResolvers(handlerMethodArgumentResolverComposite);

            handlerMethods.put(uriInfo, handlerMethod);
        }

        logger.info(annotatedMethod.size() + " Annotation Handler Method Mapped");
    }

    public HandlerMethod findHandlerMethod(String uri, RequestMethod requestMethod) {
        HandlerMethod handlerMethod = handlerMethods.get(new URIInfo(uri, requestMethod));

        return handlerMethod;
    }

    private void initializeHandlerMethodArgumentResolverComposite() {
        handlerMethodArgumentResolverComposite = new HandlerMethodArgumentResolverComposite();

        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        for (Object bean : BeanFactoryUtils.getBeanFactory().getBeans()) {
            if(HandlerMethodArgumentResolver.class.isAssignableFrom(bean.getClass())
                    && bean.getClass().isInterface() == false) {
                resolvers.add((HandlerMethodArgumentResolver) bean);
            }
        }

        handlerMethodArgumentResolverComposite.addResolver(resolvers.toArray(new HandlerMethodArgumentResolver[]{}));
    }

    private Set<Method> findAnnotatedMethods(Reflections reflections) {
        return reflections.getMethodsAnnotatedWith(RequestMapping.class);
    }

    private URIInfo createUriInfo(Method method) {
        String classURI = findClassURI(method.getDeclaringClass());
        String methodURI = findMethodURI(method);
        String uri = classURI + methodURI;

        RequestMethod requestMethod = findRequestMethod(method);

        return new URIInfo(uri,  requestMethod);
    }

    private String findClassURI(Class<?> targetClass) {
        RequestMapping requestMapping = targetClass.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }

    private String findMethodURI(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return "";
        }

        return requestMapping.value();
    }

    private RequestMethod findRequestMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        if(requestMapping == null) {
            return RequestMethod.GET;
        }

        return requestMapping.method();
    }
}