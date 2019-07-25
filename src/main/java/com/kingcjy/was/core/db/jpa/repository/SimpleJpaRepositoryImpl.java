package com.kingcjy.was.core.db.jpa.repository;

import javax.persistence.EntityManager;
import java.util.List;

public class SimpleJpaRepositoryImpl<T, ID> implements JpaRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<?> entityClass;

    public SimpleJpaRepositoryImpl(Class<?> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT t FROM " + entityClass.getName() + " AS t";

        List<T> results = (List<T>) entityManager.createQuery(query, entityClass).getResultList();
        return results;
    }

    @Override
    public List<T> findById(ID id) {
        return null;
    }

    @Override
    public T save(T target) {
        return null;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> targets) {
        return null;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void delete(T target) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public long count() {
        return 0;
    }
}
