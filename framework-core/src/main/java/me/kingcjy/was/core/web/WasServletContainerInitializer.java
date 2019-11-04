package me.kingcjy.was.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@HandlesTypes(WebApplicationInitializer.class)
public class WasServletContainerInitializer implements ServletContainerInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WasServletContainerInitializer.class);

    @Override
    public void onStartup(Set<Class<?>> webApplicationInitializerClasses, ServletContext servletContext) throws ServletException {

        List<WebApplicationInitializer> initializers = new ArrayList();

        if(webApplicationInitializerClasses != null) {
            for (Class<?> webApplicationInitializerClass : webApplicationInitializerClasses) {
                if(webApplicationInitializerClass.isInterface() == false
                        && Modifier.isAbstract(webApplicationInitializerClass.getModifiers()) == false
                        && WebApplicationInitializer.class.isAssignableFrom(webApplicationInitializerClass)) {
                    try {
                        initializers.add((WebApplicationInitializer) webApplicationInitializerClass.getDeclaredConstructor().newInstance());
                    } catch (Exception e) {
                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", e);
                    }
                }
            }
        }

        if(initializers.isEmpty()) {
            logger.info("No Was WebApplicationInitializer types detected on classpath");
            return;
        }

        logger.info(initializers.size() + " Was WebApplicationInitializers detected on classpath");

        for(WebApplicationInitializer webApplicationInitializer : initializers) {
            webApplicationInitializer.onStartup(servletContext);
        }
    }
}