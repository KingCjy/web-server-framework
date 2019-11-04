package me.kingcjy.was.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kingcjy.was.core.http.HttpStatus;
import me.kingcjy.was.core.http.MediaType;
import me.kingcjy.was.core.mvc.HandlerMapping;
import me.kingcjy.was.core.web.method.InvocableHandlerMethod;
import me.kingcjy.was.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;

    public void setHandlerMapping(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void init() {
        String basePackage = FileUtils.getBasePackage();

        logger.debug("dispatcher servlet initialized");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InvocableHandlerMethod handlerMethod = handlerMapping.getHandler(request);
        if(handlerMethod == null) {
            response.setStatus(404);
            return;
        }

        Object result = handlerMethod.invoke(request, response);

        response.setContentType(MediaType.APPLICATION_JSON);
        response.setCharacterEncoding("utf8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        logger.debug(request.getRequestURI() + " " +  request.getMethod());
    }
}
