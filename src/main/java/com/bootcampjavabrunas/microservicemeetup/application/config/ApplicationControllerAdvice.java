package com.bootcampjavabrunas.microservicemeetup.application.config;

import com.bootcampjavabrunas.microservicemeetup.domain.service.exception.EntityNotFoundException;
import com.bootcampjavabrunas.microservicemeetup.application.controller.exceptions.errors.ExceptionResponse;
import com.bootcampjavabrunas.microservicemeetup.application.controller.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    public ApplicationControllerAdvice(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)  //prenchimento incorreto dos formularios de criar e alterar
    public ResponseEntity<List<ExceptionResponse>> handle(final MethodArgumentNotValidException ex, WebRequest request) {
        List<ExceptionResponse> dto = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionResponse response = new ExceptionResponse(new Date(), mensagem, request.getDescription(false));
            dto.add(response);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(final BusinessException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class) //entidade nao encontrada
    public final ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    //a excecao mais generica e a exception e a excecao num servico rest mais generica e o internal server error
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}