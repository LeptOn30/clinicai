package com.dgw.clinicai.notification.application.port.out;

import com.dgw.clinicai.notification.domain.model.Notification;

public interface NotificationSender {
    void send(Notification notification);
}