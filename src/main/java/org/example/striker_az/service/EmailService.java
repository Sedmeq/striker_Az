package org.example.striker_az.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("noreply@strikeraz.com");

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email göndərilmədi: " + e.getMessage());
        }
    }

    public void sendWelcomeEmail(String email, String name, String strikerazId) {
        String subject = "STRIKERAZ-a xoş gəlmisiniz!";
        String body = String.format(
                "Salam %s,\n\n" +
                        "STRIKERAZ platformasına xoş gəldiniz!\n" +
                        "Sizin STRIKERAZ ID: %s\n\n" +
                        "Uğurlar diləyirik!\n" +
                        "STRIKERAZ komandası",
                name, strikerazId
        );
        sendEmail(email, subject, body);
    }

    public void sendRequestNotification(String ownerEmail, String playerName, String teamName) {
        String subject = "Yeni oyunçu müraciəti";
        String body = String.format(
                "Hörmətli komanda rəhbəri,\n\n" +
                        "%s adlı oyunçu '%s' komandasına qoşulmaq üçün müraciət göndərib.\n" +
                        "Platformada daxil olaraq müraciətə baxın.\n\n" +
                        "STRIKERAZ komandası",
                playerName, teamName
        );
        sendEmail(ownerEmail, subject, body);
    }
}
