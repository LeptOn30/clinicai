package com.dgw.clinicai.notification.infrastructure.adapter.out.email;

import com.dgw.clinicai.notification.application.port.out.NotificationSender;
import com.dgw.clinicai.notification.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * A mock implementation of the NotificationSender that simply logs the email content.
 * This is useful for local development without needing a real SMTP server.
 * It is marked as @Primary to be the default bean chosen by Spring.
 */
@Slf4j
@Component
@Profile("local") // This bean is only active when the 'local' profile is active
@RequiredArgsConstructor
public class LoggingNotificationSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        log.info("--- SIMULATING EMAIL SEND ---");
        log.info("To: {}", notification.getRecipient());
        log.info("Subject: {}", notification.getSubject());
        log.info("Body: \n{}", notification.getBody());
        log.info("--- END OF SIMULATED EMAIL ---");
    }
}