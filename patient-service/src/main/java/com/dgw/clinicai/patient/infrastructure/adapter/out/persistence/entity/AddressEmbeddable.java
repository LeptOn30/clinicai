package com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class AddressEmbeddable {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}