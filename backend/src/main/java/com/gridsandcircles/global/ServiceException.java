package com.gridsandcircles.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

  private final HttpStatus statusCode;

  public ServiceException(HttpStatus statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }
}
