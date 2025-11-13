package com.dgw.clinicai.identity.domain.model;

import lombok.Value;
import java.io.Serializable;
import java.util.UUID;

@Value
public class UserId implements Serializable {
    UUID value;
    public static UserId from(UUID value) {
        return new UserId(value);
    }
}