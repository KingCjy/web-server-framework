package com.kingcjy.was.core.mvc;

import com.kingcjy.was.core.annotations.web.RequestMethod;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodUriInfo {
    private String uri;
    private RequestMethod requestMethod;
    private String regex;

    public MethodUriInfo(String uri, RequestMethod requestMethod) {
        this.uri = uri;
        this.requestMethod = requestMethod;
        this.regex = toRegex(uri);
    }

    private String toRegex(String uri) {
        String result = uri.replaceAll("\\{(.*?)}", "(.*?)+");
        return result;
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

    boolean isMatch(String targetUri, RequestMethod method) {
        String[] targetUriArr = targetUri.split("/");
        String[] uriArr = this.regex.split("/");

        if(this.requestMethod != method) {
            return false;
        }

        if(targetUriArr.length != uriArr.length) {
            return false;
        }

        for(int i = 0; i < targetUriArr.length; i++) {
            String target = targetUriArr[i];
            String uri = uriArr[i];

            if(Pattern.compile(uri).matcher(target).find() == false) {
                return false;
            }
        }
        return true;
    }
}
