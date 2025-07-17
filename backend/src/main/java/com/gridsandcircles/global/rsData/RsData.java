package com.gridsandcircles.global.rsData;

public record RsData<T> (
    String msg,
    T data
) {
    public static <T> RsData<T> success(String msg, T data) {
        return new RsData<>(msg, data);
    }
    public static <T> RsData<T> failure(String msg) {
        return new RsData<>(msg, null);
    }
}
