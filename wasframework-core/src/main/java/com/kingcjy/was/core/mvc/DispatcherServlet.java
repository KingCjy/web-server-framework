package com.kingcjy.was.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcjy.was.core.annotations.web.RequestMethod;
import com.kingcjy.was.core.di.BeanFactory;
import com.kingcjy.was.core.di.BeanFactoryUtils;
import com.kingcjy.was.core.http.MediaType;
import com.kingcjy.was.core.http.ResponseEntity;
import com.kingcjy.was.core.web.method.HandlerMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private BeanFactory beanFactory;

    private AnnotationHandlerMapper annotationHandlerMapper;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        logger.info("DispatcherServlet initialize");
        beanFactory = BeanFactoryUtils.getBeanFactory();

        annotationHandlerMapper = new AnnotationHandlerMapper();
        objectMapper = beanFactory.getBean(ObjectMapper.class.getName(), ObjectMapper.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        HandlerMethod handlerMethod = annotationHandlerMapper.findHandlerMethod(requestURI, requestMethod);

        if(handlerMethod == null) {
            response404(response);
            return;
        }

        Object result = null;
        try {
            result = handlerMethod.invoke(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) result;

            response.setContentType(MediaType.APPLICATION_JSON);
            response.setCharacterEncoding("utf8");
            response.setStatus(responseEntity.getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        } else {
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.setCharacterEncoding("utf8");
            response.setStatus(200);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }

    }

    private void response404(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setCharacterEncoding("utf8");
        response.getWriter().write("{\"httpStatus\": 404}");
    }
}
