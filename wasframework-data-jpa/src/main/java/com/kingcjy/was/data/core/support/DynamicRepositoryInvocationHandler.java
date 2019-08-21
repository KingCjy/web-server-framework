package com.kingcjy.was.data.core.support;

import com.kingcjy.was.data.core.support.repository.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DynamicRepositoryInvocationHandler implements InvocationHandler {
    private Class<?> repositoryType;
    private Type entityType;
    private Type idType;
    private SimpleJpaRepository simpleJpaRepository;
    private EntityManager entityManager;

    public DynamicRepositoryInvocationHandler(Class<?> repositoryType, EntityManager entityManager) {
        this.repositoryType = repositoryType;

        entityType = ((ParameterizedType) repositoryType.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        idType = ((ParameterizedType) repositoryType.getGenericInterfaces()[0]).getActualTypeArguments()[1];

        this.entityManager = entityManager;

        simpleJpaRepository = new SimpleJpaRepository((Class<?>) entityType, entityManager);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        EntityTransaction transaction = entityManager.getTransaction();
        Object returnValue = null;

        try {
            transaction.begin();

            returnValue = method.invoke(simpleJpaRepository, args);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            transaction.rollback();
        }

        return returnValue;
    }

}
