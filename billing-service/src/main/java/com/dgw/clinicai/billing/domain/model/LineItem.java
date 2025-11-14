package com.dgw.clinicai.billing.domain.model;

import lombok.Getter;
import java.util.UUID;

@Getter
public class LineItem {
    private final UUID id;
    private final String serviceCode;
    private final String description;
    private final Money amount;

    LineItem(String serviceCode, String description, Money amount) {
        this.id = UUID.randomUUID();
        this.serviceCode = serviceCode;
        this.description = description;
        this.amount = amount;
    }
}