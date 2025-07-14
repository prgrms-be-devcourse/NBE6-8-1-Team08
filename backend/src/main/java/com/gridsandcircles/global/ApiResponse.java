package com.gridsandcircles.global;

public record ApiResponse<T>(String msg, T data) {

  public ApiResponse(String msg) {
    this(msg, null);
  }
}
