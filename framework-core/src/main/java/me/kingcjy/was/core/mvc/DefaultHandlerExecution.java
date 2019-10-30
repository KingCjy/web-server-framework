package me.kingcjy.was.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultHandlerExecution implements HandlerExecution {
    @Override
    public Object execute() {
        return null;
    }

//    private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExecution.class);
//
//
//    public DefaultHandlerExecution(Object instance, Method method) {
//        this.instance = instance;
//        this.method = method;
//    }
//
//    @Override
//    public Object execute() {
//        try {
//            return method.invoke(instance);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            logger.error("{} method invoke fail, error messageL {}", method, e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }
}
