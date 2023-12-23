package com.oskarro.muzikum.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage email);

    void sendEmailToUser(String subject, String sender, String text, String recipient);
}
