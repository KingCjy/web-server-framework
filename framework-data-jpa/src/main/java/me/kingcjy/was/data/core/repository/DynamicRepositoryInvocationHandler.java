package me.kingcjy.was.data.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DynamicRepositoryInvocationHandler implements InvocationHandler {

    private EntityManager entityManager;
    private SimpleJpaRepository jpaRepository;

    public DynamicRepositoryInvocationHandler(Class<?> repositoryType, EntityManager entityManager) {
        this.entityManager = entityManager;

        Type entityType = ((ParameterizedType) repositoryType.getGenericInterfaces()[0]).getActualTypeArguments()[0];

        this.jpaRepository = new SimpleJpaRepository((Class<?>) entityType, entityManager);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EntityTransaction transaction = entityManager.getTransaction();
        Object returnValue = null;

        try {
            transaction.begin();

            returnValue = method.invoke(jpaRepository, args);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

        return returnValue;
    }
}
