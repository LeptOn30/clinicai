package com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class ContactInfoEmbeddable {
    private String email;
    private String phoneNumber;
}