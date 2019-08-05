package com.kingcjy.was.core.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity<T> {
    private HttpStatus httpStatus;
    private T body;

    public ResponseEntity(T body) {
        this(body, HttpStatus.OK);
    }

    public ResponseEntity(HttpStatus httpStatus) {
        this(null, httpStatus);
    }

    public ResponseEntity(T body, HttpStatus httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

}
