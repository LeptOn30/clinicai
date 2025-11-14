package com.dgw.clinicai.patient.domain.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Patient {
    private PatientId id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private ContactInfo contactInfo;
    private Address address;

    public static Patient register(
            PatientId id,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            ContactInfo contactInfo,
            Address address
    ) {
        if (firstName == null || lastName == null || dateOfBirth == null || contactInfo == null) {
            throw new IllegalArgumentException("Patient requires first name, last name, DOB, and contact info.");
        }
        Patient patient = new Patient();
        patient.id = id;
        patient.firstName = firstName;
        patient.lastName = lastName;
        patient.dateOfBirth = dateOfBirth;
        patient.contactInfo = contactInfo;
        patient.address = address;
        return patient;
    }
}