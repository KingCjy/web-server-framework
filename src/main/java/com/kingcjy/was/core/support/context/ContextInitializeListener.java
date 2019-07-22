package com.kingcjy.was.core.support.context;

import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.Set;

@WebListener
public class ContextInitializeListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextInitializeListener.class);


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BeanFactory beanFactory = BeanFactoryUtils.initBeanFactory("com.kingcjy.was.application");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
