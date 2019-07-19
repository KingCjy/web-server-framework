package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.GetMapping;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.environment.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.server.ExportException;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private BeanFactory beanFactory;
    private RequestMapper requestMapper;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        beanFactory = BeanFactoryUtils.initBeanFactory("com.kingcjy.was.application");
        Collection<Method> methodList = beanFactory.getMethodsAnnotatedWith(RequestMapping.class);
        requestMapper = new RequestMapper();
        requestMapper.initMapping(methodList);

        objectMapper = beanFactory.getBean(ObjectMapper.class);
    }

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        logger.info("#============================================================#");
        logger.info("# API Path: " + requestURI);
        logger.info("# API Method: " + httpServletRequest.getMethod());
        logger.info("#============================================================#");

        Method method = requestMapper.findMethod(requestURI);
        String result = getResult(method);

        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(result.getBytes());
        out.flush();
        out.close();
    }

    private String getResult(Method method) {
        if(method == null) {
            return "";
        }
        String result = "";
        try {
            Object object = method.invoke(beanFactory.getBean(method.getDeclaringClass()));
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
