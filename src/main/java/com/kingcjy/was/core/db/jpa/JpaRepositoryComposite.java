package com.kingcjy.was.core.db.jpa;

import com.kingcjy.was.core.db.jpa.repository.JpaRepository;

import java.util.HashMap;

public class JpaRepositoryComposite {

    private final HashMap<Class<?>, JpaRepository> repositoryMap = new HashMap<>();

    public JpaRepositoryComposite(HashMap<Class<?>, JpaRepository> repositoryMap) {
        repositoryMap.putAll(repositoryMap);
    }

    public JpaRepositoryComposite addRepository(Class<?> type, JpaRepository jpaRepository) {
        repositoryMap.put(type, jpaRepository);
        return this;
    }

    public JpaRepository getRepository(Class<?> type) {
        return repositoryMap.get(type);
    }
}
