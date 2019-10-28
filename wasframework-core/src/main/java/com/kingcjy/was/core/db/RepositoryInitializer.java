package com.kingcjy.was.core.db;

import com.kingcjy.was.core.annotations.Order;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

@Order(2)
public class RepositoryInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        ServiceLoader<RepositorySupport> serviceLoader = ServiceLoader.load(RepositorySupport.class);

        Iterator<RepositorySupport> iterator = serviceLoader.iterator();

        BeanFactory beanFactory = BeanFactoryUtils.getBeanFactory();

        while(iterator.hasNext()) {
            RepositorySupport repositorySupport = iterator.next();

            Map<String, Object> repositories = repositorySupport.initializeReposiroties(System.getProperty("was.basePackage"));

            for (String key : repositories.keySet()) {
                beanFactory.addBean(key, null, repositories.get(key));
            }

            beanFactory.injectFields();
        }
    }
}
