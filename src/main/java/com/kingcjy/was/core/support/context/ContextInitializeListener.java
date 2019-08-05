package com.kingcjy.was.core.support.context;

import com.kingcjy.was.core.db.jpa.DynamicRepositoryInvocationHandler;
import com.kingcjy.was.core.db.jpa.JpaEntityManagerFactory;
import com.kingcjy.was.core.db.jpa.repository.JpaRepository;
import com.kingcjy.was.core.db.jpa.repository.SimpleJpaRepositoryImpl;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.util.ClassUtils;
import com.kingcjy.was.core.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebListener
public class ContextInitializeListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextInitializeListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
