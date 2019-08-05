package com.kingcjy.was.core.web;

import com.kingcjy.was.core.db.jpa.DynamicRepositoryInvocationHandler;
import com.kingcjy.was.core.db.jpa.JpaEntityManagerFactory;
import com.kingcjy.was.core.db.jpa.repository.JpaRepository;
import com.kingcjy.was.core.db.jpa.repository.SimpleJpaRepositoryImpl;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.util.ClassUtils;
import com.kingcjy.was.core.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@HandlesTypes(WebApplicationInitializer.class)
public class WasServletContainerInitializer implements ServletContainerInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WasServletContainerInitializer.class);

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        ReflectionUtils.initReflectionUtils("com.kingcjy.was.application", "com.kingcjy.was.core");

        ClassUtils.initClassUtils();

        Map beans = initializeJpaRepository();

        BeanFactoryUtils.initBeanFactory(beans);

        logger.info("onStartudp");
    }
    private Map initializeJpaRepository() {
        Class<?>[] repositories = ClassUtils.isAssignableFrom(JpaRepository.class);
        Set<Class<?>> entities = ReflectionUtils.getTypesAnnotatedWith(Entity.class);


        EntityManager entityManager = new JpaEntityManagerFactory(entities.toArray(new Class<?>[] {})).getEntityManager();

        Map<Class<?>, Object> beans = new HashMap<>();
        for (Class<?> repository : repositories) {

            if(repository.equals(JpaRepository.class) == true || repository.equals(SimpleJpaRepositoryImpl.class)) {
                continue;
            }
            Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {repository}, new DynamicRepositoryInvocationHandler(repository, entityManager));
            beans.put(repository, instance);
        }
        return beans;
    }
}
