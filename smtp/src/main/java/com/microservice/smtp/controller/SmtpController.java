package com.microservice.smtp.controller;

import com.microservice.smtp.entity.Email;
import com.microservice.smtp.service.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/smtp")
public class SmtpController {

    private EmailService emailService;

    public SmtpController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody Email email) throws MessagingException {
        String mail =emailService.sendMail(email.getFirstName(),email.getEmail(),email.getText(),email.getSubject());
        String pass = "Your mail has been sent!";
        return pass;
    }
    @GetMapping("test")
    public String test(){
        return "working fine!";
    }
}
