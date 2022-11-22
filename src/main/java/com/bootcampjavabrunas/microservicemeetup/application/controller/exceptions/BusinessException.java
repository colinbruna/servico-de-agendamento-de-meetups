package com.bootcampjavabrunas.microservicemeetup.application.controller.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String s) {
        super(s);
    }
}
