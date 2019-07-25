package com.kingcjy.was.core.support.context;

import com.kingcjy.was.application.board.Board;
import com.kingcjy.was.core.db.jpa.DynamicRepositoryInvocationHandler;
import com.kingcjy.was.core.db.jpa.JpaEntityManagerFactory;
import com.kingcjy.was.core.db.jpa.repository.JpaRepository;
import com.kingcjy.was.core.db.jpa.repository.SimpleJpaRepositoryImpl;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.util.ClassUtils;
import com.kingcjy.was.core.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebListener
public class ContextInitializeListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextInitializeListener.class);


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ReflectionUtils.initReflectionUtils("com.kingcjy.was.application", "com.kingcjy.was.core");

        ClassUtils.initClassUtils();

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


        BeanFactoryUtils.initBeanFactory(beans);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
