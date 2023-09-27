package com.microservice.smtp.service.impl;

import com.microservice.smtp.service.EmailService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.microservice.smtp.constants.EmailConstant.*;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    public String sendMail(String firstName, String email, String emailContent, String subject) {
        if (firstName == null || email == null || emailContent == null || subject == null) {
            // Handle the case where one or more input parameters are null
            logger.info("One or more input parameters are null. Email not sent.");
            return "One or more input parameters are null. Email not sent.";
        }
        try {
            Message message = createEmail(firstName, email, emailContent, subject);
            Transport smtpTransport = getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
            smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
            return "Mail Successfully sent!";
        } catch (MessagingException ex) {
            logger.info("Email Sent fail!");
            logger.error("Messaging Exception", ex);
            return "Email sending failed: " + ex.getMessage();
        }
    }

    private Message createEmail(String firstName, String email, String emailContent, String subject) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());

        try {
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email, false));
            message.setSubject("The subject of the mail " + subject);
            message.setText("Hey! " + firstName + "\n" + emailContent);
            return message;
        } catch (MessagingException ex) {
            logger.info("Email Sent fail!");
            logger.error("Messaging Exception", ex);
            throw ex;
        }
    }
    private @NotNull Session getEmailSession(){
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED,true);
        return Session.getInstance(properties,null);
    }
}