package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.*;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.environment.Environment;
import com.kingcjy.was.core.http.MediaType;
import com.kingcjy.was.core.web.context.request.RequestContextHolder;
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
            Object[] args = findArguments(method);
            Object object = method.invoke(beanFactory.getBean(method.getDeclaringClass()), args);
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object[] findArguments(Method method) {
        List<Object> result = new ArrayList<>();

        Annotation[][] annotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();

        for(int i = 0; i < parameterTypes.length; i++) {
            Annotation[] parameterAnnotations = annotations[i];
            Class<?> type = parameterTypes[i];

            if(type.equals(HttpServletRequest.class)) {
                result.add(RequestContextHolder.getRequest());
            }

            if(containsAnnotation(parameterAnnotations, RequestBody.class)) {

                try {
                    String body = getBody(RequestContextHolder.getRequest());

                    Object object = objectMapper.readValue(body, type);
                    result.add(object);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return result.toArray();
    }

    private boolean containsAnnotation(Annotation[] annotations, Class<? extends Annotation> targetAnnotation) {
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(targetAnnotation)) {
                return true;
            }
        }
        return false;
    }
    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

}
