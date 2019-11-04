package me.kingcjy.was.core.web.method.resolver;

import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.beans.factory.BeanFactory;
import me.kingcjy.was.core.beans.factory.BeanFactoryAware;
import me.kingcjy.was.core.beans.factory.InitializingBean;
import me.kingcjy.was.core.web.method.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class DefaultHandlerMethodFactory implements BeanFactoryAware, InitializingBean {

    private final HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        this.resolvers.addResolver(initArgumentResolvers());
    }

    private HandlerMethodArgumentResolver[] initArgumentResolvers() {
        Set<HandlerMethodArgumentResolver> resolvers = new HashSet();
        for (Object bean : beanFactory.getBeans()) {
            if(HandlerMethodArgumentResolver.class.isAssignableFrom(bean.getClass()) && bean.getClass().isAnnotationPresent(Component.class)) {
                resolvers.add((HandlerMethodArgumentResolver) bean);
            }
        }
        return resolvers.toArray(new HandlerMethodArgumentResolver[]{});
    }

    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        InvocableHandlerMethod invocableHandlerMethod = new InvocableHandlerMethod(bean, method);
        invocableHandlerMethod.setMessageMethodArgumentResolvers(resolvers);
        return invocableHandlerMethod;
    }

}
