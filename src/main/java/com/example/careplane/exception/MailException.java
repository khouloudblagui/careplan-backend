package com.example.careplane.exception;

public class MailException extends org.springframework.mail.MailException{
    public MailException(String message) {
        super(message);
    }
}
