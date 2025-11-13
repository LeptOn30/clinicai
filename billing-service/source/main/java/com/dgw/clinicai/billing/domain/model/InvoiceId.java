package com.dgw.clinicai.billing.domain.model;

import lombok.Value;
import java.io.Serializable;
import java.util.UUID;

@Value
public class InvoiceId implements Serializable {
    UUID value;
    public static InvoiceId from(UUID value) {
        return new InvoiceId(value);
    }
}