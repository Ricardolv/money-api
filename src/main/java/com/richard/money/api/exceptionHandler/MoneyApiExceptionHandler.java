package com.richard.money.api.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class MoneyApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageUser = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String messageDeveloper = null != ex.getCause() ? ex.getCause().toString() : ex.toString() ;
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));

        return handleExceptionInternal(ex, errors, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = createListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String messageUser = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,  WebRequest request) {
        String messageUser = messageSource.getMessage("resource.not-operation-allowed", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> errors = Arrays.asList(new Error(messageUser, messageDeveloper));

        return handleExceptionInternal(ex, errors, new HttpHeaders(), BAD_REQUEST, request);
    }

    private List<Error> createListErrors(BindingResult bindingResult) {
        List<Error> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String messageDeveloper = fieldError.toString();
            errors.add(new Error(messageUser, messageDeveloper));
        }

        return errors;
    }

    @AllArgsConstructor
    @Getter
    public static class Error {

        private String messageUser;
        private String messageDeveloper;
    }
}
