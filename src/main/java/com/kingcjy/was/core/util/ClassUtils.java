package com.kingcjy.was.core.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassUtils {

    private static Class<?>[] classes;

    public static void initClassUtils() {
        ClassUtils.classes = ReflectionUtils.getAllClasses().toArray(new Class<?>[] {});
    }

    public static Class<?>[] isAssignableFrom(Class<?> interfaceClass) {
        List<Class<?>> result = new ArrayList<>();

        for (Class<?> clazz : classes) {
            if(interfaceClass.isAssignableFrom(clazz)) {
                result.add(clazz);
            }
        }

        return result.toArray(new Class[]{});
    }
}
