package com.artcart.exception;

public class CloudinaryImageUploadException extends RuntimeException{
    public CloudinaryImageUploadException(String message){
        super(message);
    }
}
