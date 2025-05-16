package com.xcelore.doctorpatient.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String city;
    private String email;
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private Symptom symptom;

    public enum Symptom {
        ARTHRITIS, BACK_PAIN, TISSUE_INJURIES,
        DYSMENORRHEA,
        SKIN_INFECTION, SKIN_BURN,
        EAR_PAIN
    }
}