package me.kingcjy.was.core.utils;

import me.kingcjy.was.core.WasApplication;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class MyReflectionUtils {
    public static Set<Class> findAnnotatedClasses(String basePackage, Annotation... annotations) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(new ClassLoader[] { ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader() }))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage)).include(FilterBuilder.prefix(WasApplication.class.getPackage().getName()))));

        return getTypeAnnotatedClass(reflections, annotations);
    }

    private static Set<Class> getTypeAnnotatedClass(Reflections reflections, Annotation... annotations) {
        Set<Class> annotatedClasses = new HashSet<>();

        for (Annotation annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return annotatedClasses;
    }
}
