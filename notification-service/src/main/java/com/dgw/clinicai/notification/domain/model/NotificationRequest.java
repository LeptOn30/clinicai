package com.dgw.clinicai.notification.domain.model;

public record NotificationRequest(
        String recipient,
        String subject,
        String body
) {
}