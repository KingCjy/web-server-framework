package com.kingcjy.was.core.db;

import com.kingcjy.was.core.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class RepositoryInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        ServiceLoader<RepositorySupport> loader = ServiceLoader.load(RepositorySupport.class);
//
//        Iterator<RepositorySupport> iterator = loader.iterator();
//
//        while(iterator.hasNext()) {
//            RepositorySupport repositorySupport = iterator.next();
//
//            repositorySupport.initialize();
//
//            if(repositorySupport != null) {
//                break;
//            }
//        }
    }
}
