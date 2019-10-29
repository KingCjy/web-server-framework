package me.kingcjy.was.core.web;

import me.kingcjy.was.core.annotations.Bean;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.annotations.Configuration;
import me.kingcjy.was.core.di.definition.AnnotationBeanDefinition;
import me.kingcjy.was.core.di.definition.DefaultBeanDefinition;
import me.kingcjy.was.core.di.DefaultListenableBeanFactory;
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

@WebServlet(name = "dispatcher", urlPatterns = {"/*"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init() {
        String basePackage = FileUtils.getBasePackage();

        DefaultListenableBeanFactory beanFactory = new DefaultListenableBeanFactory();

        Set<Class> classes = MyReflectionUtils.findAnnotatedClasses(basePackage, Component.class);
        Set<Class> configureClasses = MyReflectionUtils.findAnnotatedClasses(basePackage, Configuration.class);

        classes.stream().filter(targetClass -> targetClass.isAnnotation() == false).forEach(targetClass -> beanFactory.registerDefinition(new DefaultBeanDefinition(targetClass)));
        configureClasses.stream().filter(targetClass -> targetClass.isAnnotation() == false).forEach(targetClass -> {
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if(method.getAnnotation(Bean.class) == null) {
                    return;
                }
                beanFactory.registerDefinition(new AnnotationBeanDefinition(targetClass, method));
            }
        });
        beanFactory.instantiateBeans();



        logger.debug("dispatcher servlet initialized");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
