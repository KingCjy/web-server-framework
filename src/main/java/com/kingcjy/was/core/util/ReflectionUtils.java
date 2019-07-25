package com.kingcjy.was.core.util;

import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {
    private static String[] basePackages;
    private static Reflections reflections;

    public static void initReflectionUtils(String... basePackages) {
        ReflectionUtils.basePackages = basePackages;
        reflections = new Reflections(basePackages);

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());


         reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner(), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackages[0])).include(FilterBuilder.prefix(basePackages[1]))));

    }

    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beanList = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beanList.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beanList;
    }

    public static Set<Class<?>> getAllClasses() {
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        return classes;
    }
}
