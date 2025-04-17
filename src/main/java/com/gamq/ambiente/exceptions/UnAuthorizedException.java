package com.gamq.ambiente.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    private String code;
    private HttpStatus status;
    private String message;
    public UnAuthorizedException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
        this.message = message;}

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
