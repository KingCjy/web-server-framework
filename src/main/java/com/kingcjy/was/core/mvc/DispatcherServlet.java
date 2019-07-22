package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.Controller;
import com.kingcjy.was.core.annotations.web.GetMapping;
import com.kingcjy.was.core.annotations.web.RequestMapping;
import com.kingcjy.was.core.annotations.web.RequestMethod;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.environment.Environment;
import com.kingcjy.was.core.http.MediaType;
import org.apache.tomcat.util.http.fileupload.RequestContext;
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
        logger.info("아니 내가먼저임");
        beanFactory = BeanFactoryUtils.getBeanFactory();
        Collection<Method> methodList = beanFactory.getMethodsAnnotatedWith(RequestMapping.class);
        requestMapper = new RequestMapper();
        requestMapper.initMapping(methodList);

//        objectMapper = beanFactory.getBean(ObjectMapper.class);

        objectMapper = new ObjectMapper();
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

        Method method = requestMapper.findMethod(requestURI, requestMethod);
        String result = getResult(method);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
        httpServletResponse.setCharacterEncoding("utf8");
        httpServletResponse.getWriter().write(result);
    }

    private String getResult(Method method) {
        if(method == null) {
            return "";
        }
        String result = "";
        try {

            for (Class<?> parameterType : method.getParameterTypes()) {
                logger.info(parameterType.getName());
            }
            Object[] args = {};
            Object object = method.invoke(beanFactory.getBean(method.getDeclaringClass()), args);
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object[] findArguments(Class<?>[] parameterTypeArr) {

        return null;
    }
}
