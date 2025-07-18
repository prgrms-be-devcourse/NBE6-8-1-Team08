package com.gridsandcircles.global;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResultResponse<Void>> handle(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(error -> (FieldError) error)
        .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
        .collect(Collectors.joining("\n"));

    return ResponseEntity.badRequest().body(new ResultResponse<>(message));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResultResponse<Void>> handle(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(new ResultResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ResultResponse<Void>> handle(NoSuchElementException ex) {
    return ResponseEntity.status(NOT_FOUND).body(new ResultResponse<>(ex.getMessage()));
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ResultResponse<Void>> handle(ServiceException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(new ResultResponse<>(ex.getMessage()));
  }
}
