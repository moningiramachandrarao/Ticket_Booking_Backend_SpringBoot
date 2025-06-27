package com.practice.springboot.exceptions;

public class SeatsNotAvailable extends RuntimeException{

    public SeatsNotAvailable(String message){
        super(message);
    }
}
