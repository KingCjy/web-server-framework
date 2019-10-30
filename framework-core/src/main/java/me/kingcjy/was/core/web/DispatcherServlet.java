package me.kingcjy.was.core.web;

import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.annotations.Configuration;
import me.kingcjy.was.core.di.BeanFactory;
import me.kingcjy.was.core.di.BeanFactoryAware;
import me.kingcjy.was.core.di.definition.AnnotationBeanDefinition;
import me.kingcjy.was.core.di.definition.DefaultBeanDefinition;
import me.kingcjy.was.core.di.DefaultBeanFactory;
import me.kingcjy.was.core.mvc.HandlerExecution;
import me.kingcjy.was.core.mvc.HandlerMapping;
import me.kingcjy.was.core.mvc.method.HandlerMethod;
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

public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;

    private HandlerExecution handlerExecution;

    public void setHandlerMapping(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void setHandlerExecution(HandlerExecution handlerExecution) {
        this.handlerExecution = handlerExecution;
    }

    @Override
    public void init() {
        String basePackage = FileUtils.getBasePackage();

        logger.debug("dispatcher servlet initialized");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HandlerMethod handlerMethod = handlerMapping.getHandler(request);
        if(handlerMethod == null) {
            response.setStatus(404);
            return;
        }

        Object result = handlerExecution.execute();

    }
}
