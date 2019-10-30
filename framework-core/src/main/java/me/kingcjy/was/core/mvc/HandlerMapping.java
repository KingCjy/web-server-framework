package me.kingcjy.was.core.mvc;

import me.kingcjy.was.core.mvc.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    HandlerMethod getHandler(HttpServletRequest request);
}
