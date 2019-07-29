package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMethod;

import java.util.Objects;

public class MethodUriInfo {
    private String uri;
    private RequestMethod requestMethod;

    public MethodUriInfo(String uri, RequestMethod requestMethod) {
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
        MethodUriInfo that = (MethodUriInfo) o;
        return Objects.equals(uri, that.uri) &&
                requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, requestMethod);
    }
}
