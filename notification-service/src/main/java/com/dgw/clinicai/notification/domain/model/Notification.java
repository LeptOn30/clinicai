package com.dgw.clinicai.notification.domain.model;

import lombok.Value;

@Value
public class Notification {
    String recipient; // e.g., email address
    String subject;
    String body;
}