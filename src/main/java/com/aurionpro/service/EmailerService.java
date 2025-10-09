package com.aurionpro.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailerService {

    private final JavaMailSender mailSender;

    public EmailerService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send welcome email to newly added employee with temporary login credentials.
     */
    public void sendWelcomeEmailWithTempPassword(String toEmail, String tempPassword, String organizationName) throws MessagingException {
        String subject = "Welcome to " + organizationName + " - Your Login Credentials";
        String content = "<p>Dear User,</p>" +
                "<p>You have been added as an employee at <b>" + organizationName + "</b>.</p>" +
                "<p>Please use the following temporary credentials to log in and remember to change your password after first login:</p>" +
                "<p><strong>Email:</strong> " + toEmail + "</p>" +
                "<p><strong>Temporary Password:</strong> " + tempPassword + "</p>" +
                "<br>" +
                "<p>Thank you,<br>" +
                organizationName + " Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true); 

        mailSender.send(message);
    }
}
