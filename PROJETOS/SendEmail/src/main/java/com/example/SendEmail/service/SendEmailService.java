package com.example.SendEmail.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.SendEmail.exception.SendEmailException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmailService {

    private final JavaMailSender mailSender;

    public SendEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTextEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("joaopauloaramuni@gmail.com");

            mailSender.send(message);

        } catch (MailException e) {
            throw new SendEmailException("Falha ao enviar e-mail Texto: " + e.getMessage());
        }
    }

    public void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("joaopauloaramuni@gmail.com");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (MailException | MessagingException e) {
            throw new SendEmailException("Falha ao enviar e-mail HTML: " + e.getMessage());
        }
    }
}