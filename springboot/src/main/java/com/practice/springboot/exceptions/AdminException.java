package com.practice.springboot.exceptions;

public class AdminException extends RuntimeException{
   public AdminException(String message){
        super(message);
    }
}
