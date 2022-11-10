package com.bootcampjavabrunas.microservicemeetup.domain.service.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String s) {
        super(s);
    }
}
