package com.microservice.smtp.service;

import javax.mail.MessagingException;

public interface EmailService {
    public String sendMail(String firstName, String email, String emailContent, String subject) throws MessagingException;
}
