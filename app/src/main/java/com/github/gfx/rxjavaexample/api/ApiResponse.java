package com.github.gfx.rxjavaexample.api;

public class ApiResponse<T> {

    public final T content;

    public ApiResponse(T content) {
        this.content = content;
    }
}
