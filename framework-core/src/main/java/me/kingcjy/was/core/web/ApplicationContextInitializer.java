package me.kingcjy.was.core.web;

import me.kingcjy.was.core.context.AnnotationConfigApplicationContext;
import me.kingcjy.was.core.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationContextInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String baseClassName = System.getProperty("was.baseClass");
        try {
            Class baseClass = getClass().getClassLoader().loadClass(baseClassName);
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(baseClass);

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.setBeanFactory(applicationContext);
            dispatcherServlet.init();

            ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
