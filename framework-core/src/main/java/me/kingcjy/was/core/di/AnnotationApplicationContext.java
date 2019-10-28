package me.kingcjy.was.core.di;

import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.utils.MyReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class AnnotationApplicationContext extends ApplicationContext {

    private final Logger logger = LoggerFactory.getLogger(AnnotationApplicationContext.class);

    public AnnotationApplicationContext(String basePackage) {
        this.initializeBeanFactory(basePackage);
    }

    private void initializeBeanFactory(String basePackage) {
        Set<Class> classes = MyReflectionUtils.findAnnotatedClasses(basePackage);
        createInstances(classes);
    }

    private void createInstances(Set<Class> classes) {
        classes.forEach(targetClass -> {
            try {
                Object instance = targetClass.getConstructor().newInstance();

                this.beans.put(targetClass.getName(), instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error(targetClass.getName() + " hasn't no argument constructor");
                e.printStackTrace();
            }
        });

        this.injectInstanceFields();
    }

    private void injectInstanceFields() {
        for (Object instance : this.getBeans()) {
            Field[] fields = instance.getClass().getDeclaredFields();
            injectFields(instance, fields);
        }
    }

    private void injectFields(Object instance, Field[] fields) {
        for (Field field : fields) {
            if(field.getDeclaredAnnotation(Autowired.class) == null) {
                continue;
            }

            field.setAccessible(true);
            try {
                field.set(instance, this.beans.get(instance.getClass().getName()));
            } catch (IllegalAccessException e) {
                logger.error("no bean named " + field.getName());
                e.printStackTrace();
            }
        }
    }
}
