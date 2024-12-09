package com.example.autowash.ApiResponse;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}