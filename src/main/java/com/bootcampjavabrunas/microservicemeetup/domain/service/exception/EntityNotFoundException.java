package com.bootcampjavabrunas.microservicemeetup.domain.service.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String exception) {
        super(exception);
    }
}
