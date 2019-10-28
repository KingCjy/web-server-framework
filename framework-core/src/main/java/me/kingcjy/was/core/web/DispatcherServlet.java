package me.kingcjy.was.core.web;

import me.kingcjy.was.core.di.AnnotationApplicationContext;
import me.kingcjy.was.core.utils.FileUtils;
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

    @Override
    public void init() {
        String basePackage = FileUtils.getBasePackage();
        AnnotationApplicationContext annotationApplicationContext = new AnnotationApplicationContext(basePackage);

        logger.debug("dispatcher servlet initialized");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
