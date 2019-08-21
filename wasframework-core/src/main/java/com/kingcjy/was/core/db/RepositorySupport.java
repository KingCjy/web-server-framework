package com.kingcjy.was.core.db;

import com.kingcjy.was.core.di.BeanFactory;

import java.util.Map;

public abstract class RepositorySupport {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void initialize() {
        String basePackage = System.getProperty("was.basePackage");
        Map<String, Object> repositories = initializeReposiroties(basePackage);

        for (String key : repositories.keySet()) {
            beanFactory.addBean(key, null, repositories.get(key));
        }

        beanFactory.injectFields();
    }

    protected abstract Map<String, Object> initializeReposiroties(String basePackage);
}
