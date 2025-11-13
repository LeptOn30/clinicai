package com.dgw.clinicai.notification.application.port.in;

import com.dgw.clinicai.notification.domain.model.NotificationRequest;

public interface SendNotificationUseCase {
    void sendNotification(NotificationRequest request);
}