package com.kingcjy.was.core.web.servlet;

import javax.servlet.Filter;

public class FilterRegistrationBean<T extends Filter> {
    private T filter;

    public FilterRegistrationBean() {}

    public T getFilter() {
        return this.filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }
}
