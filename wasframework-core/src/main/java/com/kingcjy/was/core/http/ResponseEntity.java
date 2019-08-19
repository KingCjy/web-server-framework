package com.kingcjy.was.core.http;

public class ResponseEntity<T> {
    private HttpStatus httpStatus;
    private T body;

    public ResponseEntity(T body) {
        this(body, HttpStatus.OK);
    }

    public ResponseEntity(T body, HttpStatus httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
