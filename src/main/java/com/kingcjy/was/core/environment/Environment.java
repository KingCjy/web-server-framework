package com.kingcjy.was.core.environment;

public class Environment {
    public static String basePackage;

    public static void setBasePackage(String basePackage) {
        Environment.basePackage = basePackage;
    }

    public static String getBasePackage() {
        return basePackage;
    }
}
