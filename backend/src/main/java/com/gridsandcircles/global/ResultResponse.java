package com.gridsandcircles.global;

public record ResultResponse<T>(String msg, T data) {

  public ResultResponse(String msg) {
    this(msg, null);
  }
}
