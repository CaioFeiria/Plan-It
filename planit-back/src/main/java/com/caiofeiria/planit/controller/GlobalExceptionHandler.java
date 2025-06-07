package com.caiofeiria.planit.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.caiofeiria.planit.dtos.ApiError;
import com.caiofeiria.planit.exceptions.invalid.InvalidException;
import com.caiofeiria.planit.exceptions.nocontent.NoContentException;
import com.caiofeiria.planit.exceptions.notfound.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e) {
        var apiError = getApiError(HttpStatus.INTERNAL_SERVER_ERROR, List.of(e.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ApiError> handleNotContentException(NoContentException e) {
        var apiError = getApiError(HttpStatus.NO_CONTENT, List.of(e.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        var apiError = getApiError(HttpStatus.NOT_FOUND, List.of(e.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiError> handleInvalidException(InvalidException e) {
        var apiError = getApiError(HttpStatus.BAD_REQUEST, List.of(e.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String mensagem = "Valor inválido fornecido para um campo do tipo enum. Utilize as opções BAIXO, MEDIO, URGENTE, ALTO";

        var apiError = getApiError(HttpStatus.BAD_REQUEST, List.of(mensagem));
            
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        var apiError = getApiError(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private ApiError getApiError(HttpStatus httpStatus, List<String> errors) {
        return new ApiError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.name(),
                errors
        );
    }
}
