package com.practice.springboot.exceptions;

public class OutOfRangeException extends RuntimeException{
    public OutOfRangeException(String message){
        super(message);
    }
}
