package com.jijy.music.services.exceptions;

public class BadRequestFailed extends RuntimeException{
    public BadRequestFailed(String message){
        super(message);
    }
}
