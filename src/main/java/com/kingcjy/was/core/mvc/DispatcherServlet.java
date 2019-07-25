package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.*;
import com.kingcjy.was.core.converter.HttpMessageConverter;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.environment.Environment;
import com.kingcjy.was.core.http.MediaType;
import com.kingcjy.was.core.web.context.request.RequestContextHolder;
import com.kingcjy.was.core.web.method.HandlerMethod;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private BeanFactory beanFactory;
    private RequestMapper requestMapper;

    private ObjectMapper objectMapper;
    private HttpMessageConverter converter;

    @Override
    public void init() {
        beanFactory = BeanFactoryUtils.getBeanFactory();
        Collection<Method> methodList = beanFactory.getMethodsAnnotatedWith(RequestMapping.class);
        requestMapper = new RequestMapper();
        requestMapper.initMapping(methodList);



//        objectMapper = beanFactory.getBean(ObjectMapper.class);

        objectMapper = new ObjectMapper();
        converter = new HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
    }

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(httpServletRequest.getMethod());
        logger.info("#============================================================#");
        logger.info("# API Path: " + requestURI);
        logger.info("# API Method: " + requestMethod.name());
        logger.info("#============================================================#");

        String result = "";
        try {
            result = requestMapper.onRequest(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
        httpServletResponse.setCharacterEncoding("utf8");
        httpServletResponse.getWriter().write(result);
    }
}
