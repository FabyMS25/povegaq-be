package com.gamq.ambiente.exceptions;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
    private String code;
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

