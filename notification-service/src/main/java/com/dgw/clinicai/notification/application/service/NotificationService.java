package com.dgw.clinicai.notification.application.service;

import com.dgw.clinicai.notification.application.port.in.SendNotificationUseCase;
import com.dgw.clinicai.notification.application.port.out.NotificationSender;
import com.dgw.clinicai.notification.domain.model.Notification;
import com.dgw.clinicai.notification.domain.model.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService implements SendNotificationUseCase {

    private final NotificationSender emailSender;

    @Override
    public void sendNotification(NotificationRequest request) {
        // In a real system, you might fetch patient preferences here.
        // For now, we'll just create an email notification.
        log.info("Preparing notification for recipient: {}", request.recipient());

        Notification notification = new Notification(
                request.recipient(),
                request.subject(),
                request.body());

        emailSender.send(notification);
    }
}