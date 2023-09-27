package com.microservice.smtp.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Email {
    private String firstName;
    private String email;
    private String subject;
    private String text;

    public Email() {
    }

    public Email(String firstName, String email, String subject, String text) {
        this.firstName = firstName;
        this.email = email;
        this.subject = subject;
        this.text = text;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
