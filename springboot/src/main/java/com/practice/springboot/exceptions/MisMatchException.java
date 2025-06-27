package com.practice.springboot.exceptions;

public class MisMatchException extends RuntimeException{

    public MisMatchException(String message){
        super(message);
    }
}
