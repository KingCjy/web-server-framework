package me.kingcjy.was.core.web;

import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.annotations.Configuration;
import me.kingcjy.was.core.di.BeanFactory;
import me.kingcjy.was.core.di.BeanFactoryAware;
import me.kingcjy.was.core.di.definition.AnnotationBeanDefinition;
import me.kingcjy.was.core.di.definition.DefaultBeanDefinition;
import me.kingcjy.was.core.di.DefaultBeanFactory;
import me.kingcjy.was.core.utils.FileUtils;
import me.kingcjy.was.core.utils.MyReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

public class DispatcherServlet extends HttpServlet implements BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void init() {
        String basePackage = FileUtils.getBasePackage();

        logger.debug("dispatcher servlet initialized");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("aa");
    }
}
