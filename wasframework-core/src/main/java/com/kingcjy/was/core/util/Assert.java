package com.kingcjy.was.core.util;

public class Assert {
    private Assert() {}

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - object nust not be null");
    }

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}