package me.kingcjy.was.data.core.repository.query;

public class QueryNotFoundException extends RuntimeException {

    public QueryNotFoundException() {

    }

    public QueryNotFoundException(String message) {
        super(message);
    }
}
