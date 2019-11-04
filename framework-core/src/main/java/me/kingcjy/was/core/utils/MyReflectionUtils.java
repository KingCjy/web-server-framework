package me.kingcjy.was.core.utils;

import me.kingcjy.was.core.WasApplication;
import me.kingcjy.was.core.web.method.HandlerMethod;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyReflectionUtils {

    private static Map<String, Reflections> reflectionsCache = new HashMap<>();

    private static Reflections createReflections(String basePackage) {
        if(reflectionsCache.get(basePackage) == null) {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false ), new ResourcesScanner(), new TypeAnnotationsScanner())
                    .setUrls(ClasspathHelper.forClassLoader(new ClassLoader[] { ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader() }))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage)).include(FilterBuilder.prefix("me.kingcjy.was"))));
            reflectionsCache.put(basePackage, reflections);
            return reflections;
        }

        return reflectionsCache.get(basePackage);
    }
    public static Set<Class> findAnnotatedClasses(String basePackage, Class<? extends Annotation>... annotations) {
        Reflections reflections = createReflections(basePackage);
        return getTypeAnnotatedClass(reflections, annotations);
    }

    public static Set<Class> findAssignableClass(String baseClass, Class targetInterface) {
        Reflections reflections = createReflections(baseClass);
        Set<Class> classes = reflections.getSubTypesOf(targetInterface);

        return classes;
    }

    private static Set<Class> getTypeAnnotatedClass(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class> annotatedClasses = new HashSet<>();

        for (Class<? extends Annotation> annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return annotatedClasses;
    }
}
