package com.dgw.clinicai.patient.domain.model;

import lombok.Value;

@Value
public class Address {
    String street;
    String city;
    String state;
    String zipCode;
}