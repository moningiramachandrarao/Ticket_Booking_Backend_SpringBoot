package com.practice.springboot.exceptions;

public class OccupiedException extends RuntimeException{

    public OccupiedException(String message){
        super(message);
    }
}
