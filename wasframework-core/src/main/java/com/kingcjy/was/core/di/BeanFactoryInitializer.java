package com.kingcjy.was.core.di;

import com.kingcjy.was.core.annotations.Autowired;
import com.kingcjy.was.core.annotations.Order;
import com.kingcjy.was.core.web.WebApplicationInitializer;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Order(1)
public class BeanFactoryInitializer implements WebApplicationInitializer {

    private static Logger logger = LoggerFactory.getLogger(BeanFactoryInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initializeBeanFactory();
    }

    private void initializeBeanFactory() {
        BeanFactory beanFactory = new DefaultBeanFactory();

        Set<Class<?>> scanClasses = scanAllClasses();

        initializeBeans(scanClasses, beanFactory);
        initializeConfigBeans(scanClasses, beanFactory);
        injectFields(beanFactory);

        BeanFactoryUtils.setBeanFactory(beanFactory);
    }

    private void initializeBeans(Set<Class<?>> scanClasses, BeanFactory beanFactory) {

        for (Class<?> scanClass : scanClasses) {
            boolean isBean = BeanFactoryUtils.isBean(scanClass);

            if(isBean == false) {
                continue;
            }

            try {
                beanFactory.addBean(scanClass.getName(), null, scanClass.getDeclaredConstructor().newInstance());
                logger.info("bean name " + scanClass.getName() + " is initialized");
            } catch(InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeConfigBeans(Set<Class<?>> scanClasses, BeanFactory beanFactory) {
        for (Class<?> scanClass : scanClasses) {
            boolean isConfigurationClass = BeanFactoryUtils.isConfigurationClass(scanClass);

            if(isConfigurationClass == false) {
                continue;
            }

            try {
                Object instance = scanClass.getDeclaredConstructor().newInstance();

                for (Method method : scanClass.getDeclaredMethods()) {
                    method.setAccessible(true);
                    Object beanInstance = method.invoke(instance);
                    beanFactory.addBean(method.getReturnType().getName(), null, beanInstance);

                    logger.info("bean name " + method.getReturnType() + " is initialized");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void injectFields(BeanFactory beanFactory) {
        for (Object bean : beanFactory.getBeans()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if(field.getAnnotation(Autowired.class) != null) {
                    field.setAccessible(true);
                    try {
                        field.set(bean, beanFactory.getBean(field.getType().getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Set<Class<?>> scanAllClasses() {
        String basePackage = System.getProperty("was.basePackage");

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false ), new ResourcesScanner(), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.kingcjy.was.core")).include(FilterBuilder.prefix(basePackage))));

        return reflections.getSubTypesOf(Object.class);
    }
}
