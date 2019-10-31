package me.kingcjy.was.core.mvc;

import me.kingcjy.was.core.annotations.web.RequestMethod;

import java.util.Objects;

public class HandlerKey {
    private String uri;
    private RequestMethod requestMethod;

    public HandlerKey(String uri, RequestMethod requestMethod) {
        this.uri = uri;
        this.requestMethod = requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public boolean matches(String uri, RequestMethod requestMethod) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(uri, that.uri) &&
                requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, requestMethod);
    }

    @Override
    public String toString() {
        return "HandlerKey{" +
                "uri='" + uri + '\'' +
                ", requestMethod=" + requestMethod +
                '}';
    }
}
