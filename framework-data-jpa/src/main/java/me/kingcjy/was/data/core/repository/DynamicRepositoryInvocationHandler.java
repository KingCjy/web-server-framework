package me.kingcjy.was.data.core.repository;

import me.kingcjy.was.data.core.repository.query.QueryExecutor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class DynamicRepositoryInvocationHandler implements InvocationHandler {

    private EntityManager entityManager;
    private SimpleJpaRepository jpaRepository;
    private Map<Method, QueryExecutor> queryExecutors = new HashMap<>();


    public DynamicRepositoryInvocationHandler(Class<?> repositoryType, EntityManager entityManager) {
        this.entityManager = entityManager;

        Type entityType = ((ParameterizedType) repositoryType.getGenericInterfaces()[0]).getActualTypeArguments()[0];

        this.jpaRepository = new SimpleJpaRepository((Class<?>) entityType, entityManager);

        initializeQueryExecutors(repositoryType);
    }

    public void initializeQueryExecutors(Class<?> repositoryType) {
        for (Method method : repositoryType.getDeclaredMethods()) {
            queryExecutors.put(method, new QueryExecutor(method));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EntityTransaction transaction = entityManager.getTransaction();
        Object returnValue = null;

        try {
            transaction.begin();

            if(isJpaRepositoryMethod(method)) {
                returnValue = executeSimpleJpaRepositoryMethod(method, args);
            } else {
                returnValue = findQueryExecutor(method).execute(this.entityManager, args);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

        return returnValue;
    }

    private Object executeSimpleJpaRepositoryMethod(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(jpaRepository, args);
    }

    private boolean isJpaRepositoryMethod(Method method) {
        try {
            SimpleJpaRepository.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private QueryExecutor findQueryExecutor(Method method) {
        return queryExecutors.get(method);
    }
}
