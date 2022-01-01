package com.oskarro.muzikum.user.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Class invokes JavaMailSender with SimpleMailMessage components.
 * It models a simple mail message, including data such as the from, to, cc, subject and text fields
 *
 * Clients should talk to the mail sender through this interface implementation if they need mail functionality
 * */

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        System.out.println("Email sending from " + email.getFrom());
        javaMailSender.send(email);
    }
}
