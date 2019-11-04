package me.kingcjy.was.core.mvc;

import me.kingcjy.was.core.web.method.InvocableHandlerMethod;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    InvocableHandlerMethod getHandler(HttpServletRequest request);
}
