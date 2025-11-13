package com.dgw.clinicai.notification.infrastructure.adapter.out.email;

import com.dgw.clinicai.notification.application.port.out.NotificationSender;
import com.dgw.clinicai.notification.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!local") // This bean is active when the 'local' profile is NOT active
@RequiredArgsConstructor
public class SmtpNotificationSender implements NotificationSender {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void send(Notification notification) {
        try {
            log.info("Sending real email to: {}", notification.getRecipient());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(notification.getRecipient());
            message.setSubject(notification.getSubject());
            message.setText(notification.getBody());

            javaMailSender.send(message);
            log.info("Successfully sent email to: {}", notification.getRecipient());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", notification.getRecipient(), e);
            // In a real system, you might re-queue this notification or alert an operator.
        }
    }
}