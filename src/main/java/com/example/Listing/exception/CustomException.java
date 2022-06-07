package com.example.Listing.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CustomException extends RuntimeException{

    /**
     *
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;

    public CustomException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CustomException() {

    }
}
