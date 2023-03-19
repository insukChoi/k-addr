package com.example.kaddr.api;

public interface ApiClient<T> {
    String uri();

    String key();

    T callApi(String keyword);

    default String requestType() {
        return "application/json";
    }

    default String responseType() {
        return "json";
    }
}