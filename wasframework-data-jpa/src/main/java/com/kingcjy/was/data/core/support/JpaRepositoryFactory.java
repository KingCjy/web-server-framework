package com.kingcjy.was.data.core.support;

import com.kingcjy.was.core.db.RepositorySupport;
import com.kingcjy.was.data.core.support.repository.JpaRepository;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JpaRepositoryFactory implements RepositorySupport {

    private JpaEntityManagerFactory entityManagerFactory;

    @Override
    public Map<String, Object> initializeReposiroties(String basePackage) {

        Map<String, Object> repositoryBeans = new HashMap<>();

        Set<Class<?>> entities = findEntities(basePackage);
        Set<Class<? extends JpaRepository>> repositories = findRepositoryClasses(basePackage);

        this.entityManagerFactory = new JpaEntityManagerFactory(entities.toArray(new Class<?>[] {}));

        for (Class<? extends JpaRepository> repository : repositories) {
            Object instance = createInstance(repository);

            repositoryBeans.put(repository.getName(), instance);
        }

        return repositoryBeans;
    }

    private Set<Class<?>> findEntities(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    private Set<Class<? extends JpaRepository>> findRepositoryClasses(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends JpaRepository>> repositories = reflections.getSubTypesOf(JpaRepository.class);


        return repositories;
    }

    private Object createInstance(Class<? extends JpaRepository> targetClass) {
        Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { targetClass }, new DynamicRepositoryInvocationHandler(targetClass, this.entityManagerFactory.getEntityManager()));
        return instance;
    }

}
