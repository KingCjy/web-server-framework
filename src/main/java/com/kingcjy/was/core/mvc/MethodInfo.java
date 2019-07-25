package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMethod;

import java.util.Objects;

public class MethodInfo {
    private String uri;
    private RequestMethod requestMethod;

    public MethodInfo(String uri, RequestMethod requestMethod) {
        this.uri = uri;
        this.requestMethod = requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodInfo that = (MethodInfo) o;
        return Objects.equals(uri, that.uri) &&
                requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, requestMethod);
    }
}
