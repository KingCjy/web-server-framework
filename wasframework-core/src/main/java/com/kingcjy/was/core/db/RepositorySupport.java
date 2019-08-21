package com.kingcjy.was.core.db;

import com.kingcjy.was.core.di.BeanFactory;

import java.util.Map;

public interface RepositorySupport {
    Map<String, Object> initializeReposiroties(String basePackage);
}
