package me.kingcjy.was.core.utils;

import me.kingcjy.was.core.WasApplication;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class MyReflectionUtils {
    public static Set<Class> findAnnotatedClasses(String basePackage, Class<? extends Annotation>... annotations) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false ), new ResourcesScanner(), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(new ClassLoader[] { ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader() }))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage)).include(FilterBuilder.prefix(WasApplication.class.getPackage().getName()))));

        return getTypeAnnotatedClass(reflections, annotations);
    }

    private static Set<Class> getTypeAnnotatedClass(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class> annotatedClasses = new HashSet<>();

        for (Class<? extends Annotation> annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return annotatedClasses;
    }
}
