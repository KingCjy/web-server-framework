package me.kingcjy.was.data.core.repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {
    private EntityManager entityManager;
    private final Class<?> entityClass;

    public SimpleJpaRepository(Class<?> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT t FROM " + entityClass.getName() + " AS t";

        List<T> results = (List<T>) entityManager.createQuery(query, entityClass).getResultList();
        return results;
    }

    @Override
    public T findById(ID id) {
        T entity = (T) entityManager.find(entityClass, id);
        return entity;
    }

    @Override
    public T save(T target) {
        entityManager.persist(target);
        return target;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> targets) {
        List<T> result = new ArrayList<>();
        targets.forEach(target -> {
            result.add(this.save(target));
        });
        return result;
    }

    @Override
    public void deleteById(ID id) {
        T target = this.findById(id);
        entityManager.remove(target);
    }

    @Override
    public void delete(T target) {
        entityManager.remove(target);
    }

    @Override
    public void deleteAll() {
        List<T> targets = this.findAll();

        targets.forEach(target -> {
            this.delete(target);
        });
    }
}
