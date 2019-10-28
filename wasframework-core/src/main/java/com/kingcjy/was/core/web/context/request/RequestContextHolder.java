package com.kingcjy.was.core.web.context.request;

import javax.servlet.http.HttpServletRequest;

public class RequestContextHolder {
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    private RequestContextHolder() { }

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static void setRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }
}
