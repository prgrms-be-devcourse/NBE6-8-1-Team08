package com.gridsandcircles.global;

import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handle(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(error -> (FieldError) error)
        .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
        .collect(Collectors.joining("\n"));

    return ResponseEntity.badRequest().body(new ApiResponse<>(message));
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ApiResponse<Void>> handle(ServiceException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(new ApiResponse<>(ex.getMessage()));
  }
}
