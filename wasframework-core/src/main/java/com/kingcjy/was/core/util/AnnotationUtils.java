package com.kingcjy.was.core.util;

import java.lang.annotation.Annotation;

public class AnnotationUtils {
    public static boolean hasAnotation(Class<?> targetClass, Class<? extends Annotation> annotation) {

        if(targetClass.getAnnotation(annotation) != null) {
            return true;
        }

        for (Annotation targetClassAnnotation : targetClass.getAnnotations()) {
            if(targetClassAnnotation.annotationType().getAnnotation(annotation) != null) {
                return true;
            }
        }

        return false;
    }
}
