package com.oskarro.muzikum.user.email;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        log.info("Sending email from {} to {}: {}", email.getFrom(), email.getTo(), email.getSubject());
        javaMailSender.send(email);
    }
}
