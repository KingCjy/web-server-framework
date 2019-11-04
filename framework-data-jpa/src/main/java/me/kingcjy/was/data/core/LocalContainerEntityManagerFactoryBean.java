package me.kingcjy.was.data.core;

import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.data.core.jpa.EntityManagerProxy;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Map;

@Component(value = "javax.persistence.EntityManager")
public class LocalContainerEntityManagerFactoryBean implements EntityManagerProxy {

    private ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public EntityManager getTargetEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();

        if(entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }

        return entityManager;
    }

    @Override
    public void persist(Object o) {
        getTargetEntityManager().persist(o);
    }

    @Override
    public <T> T merge(T t) {
        return getTargetEntityManager().merge(t);
    }

    @Override
    public void remove(Object o) {
        getTargetEntityManager().remove(o);
    }

    @Override
    public <T> T find(Class<T> aClass, Object o) {
        return getTargetEntityManager().find(aClass, o);
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return getTargetEntityManager().find(aClass, o, map);
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return getTargetEntityManager().find(aClass, o, lockModeType);
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return getTargetEntityManager().find(aClass, o, lockModeType, map);
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return getTargetEntityManager().getReference(aClass, o);
    }

    @Override
    public void flush() {
        getTargetEntityManager().flush();
    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {
        getTargetEntityManager().setFlushMode(flushModeType);
    }

    @Override
    public FlushModeType getFlushMode() {
        return getTargetEntityManager().getFlushMode();
    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {
        getTargetEntityManager().lock(o, lockModeType);
    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
        getTargetEntityManager().lock(o, lockModeType, map);
    }

    @Override
    public void refresh(Object o) {
        getTargetEntityManager().refresh(o);
    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {
        getTargetEntityManager().refresh(o, map);
    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {
        getTargetEntityManager().refresh(o, lockModeType);
    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {
        getTargetEntityManager().refresh(o, lockModeType, map);
    }

    @Override
    public void clear() {
        getTargetEntityManager().clear();
    }

    @Override
    public void detach(Object o) {
        getTargetEntityManager().detach(o);
    }

    @Override
    public boolean contains(Object o) {
        return getTargetEntityManager().contains(o);
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return getTargetEntityManager().getLockMode(o);
    }

    @Override
    public void setProperty(String s, Object o) {
        getTargetEntityManager().setProperty(s, o);
    }

    @Override
    public Map<String, Object> getProperties() {
        return getTargetEntityManager().getProperties();
    }

    @Override
    public Query createQuery(String s) {
        return getTargetEntityManager().createQuery(s);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return getTargetEntityManager().createQuery(criteriaQuery);
    }

    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return getTargetEntityManager().createQuery(criteriaUpdate);
    }

    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return getTargetEntityManager().createQuery(criteriaDelete);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return getTargetEntityManager().createQuery(s, aClass);
    }

    @Override
    public Query createNamedQuery(String s) {
        return getTargetEntityManager().createNamedQuery(s);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return getTargetEntityManager().createNamedQuery(s, aClass);
    }

    @Override
    public Query createNativeQuery(String s) {
        return getTargetEntityManager().createNativeQuery(s);
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        return getTargetEntityManager().createNativeQuery(s, aClass);
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        return getTargetEntityManager().createNativeQuery(s, s1);
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return getTargetEntityManager().createNamedStoredProcedureQuery(s);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return getTargetEntityManager().createStoredProcedureQuery(s);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return getTargetEntityManager().createStoredProcedureQuery(s, classes);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return getTargetEntityManager().createStoredProcedureQuery(s, strings);
    }

    @Override
    public void joinTransaction() {
        getTargetEntityManager().joinTransaction();
    }

    @Override
    public boolean isJoinedToTransaction() {
        return getTargetEntityManager().isJoinedToTransaction();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return getTargetEntityManager().unwrap(aClass);
    }

    @Override
    public Object getDelegate() {
        return getTargetEntityManager().getDelegate();
    }

    @Override
    public void close() {
        getTargetEntityManager().close();
    }

    @Override
    public boolean isOpen() {
        return getTargetEntityManager().isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return getTargetEntityManager().getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return getTargetEntityManager().getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return getTargetEntityManager().getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return getTargetEntityManager().getMetamodel();
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return getTargetEntityManager().createEntityGraph(aClass);
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return getTargetEntityManager().createEntityGraph(s);
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return getTargetEntityManager().getEntityGraph(s);
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return getTargetEntityManager().getEntityGraphs(aClass);
    }
}
