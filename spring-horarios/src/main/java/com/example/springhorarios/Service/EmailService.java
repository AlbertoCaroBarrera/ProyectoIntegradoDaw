package com.example.springhorarios.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    
    public void sendDefaultPasswordEmail(String to, String defaultPassword) {
        String subject = "Your Account Has Been Created";
        String url = "https://horarios.up.railway.app";
        String text = "<html>" +
                "<body>" +
                "<h1>Welcome!</h1>" +
                "<p>Your account has been created. Below are your login details:</p>" +
                "<p><strong>Username:</strong> " + to + "</p>" +
                "<p><strong>Password:</strong> " + defaultPassword + "</p>" +
                "<p>Please change your password after your first login.</p>" +
                "<a href=\"" + url + "\">Iniciar sesion</a>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, text);
    }

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String url = "https://horarios.up.railway.app/reset-password?token=" + token;
        String text = "<html>" +
                "<body>" +
                "<h1>Password Reset Request</h1>" +
                "<p>To reset your password, click the link below:</p>" +
                "<a href=\"" + url + "\">Reset Password</a>" +
                "<p>If you did not request a password reset, please ignore this email.</p>" +
                "</body>" +
                "</html>";

        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            System.out.println("Sending email to: " + to); // Registro de depuración
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            System.out.println("Email sent successfully"); // Registro de depuración
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}