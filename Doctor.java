package com.xcelore.doctorpatient.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private City city;
    
    private String email;
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    public enum City { DELHI, NOIDA, FARIDABAD }
    public enum Speciality { ORTHOPAEDIC, GYNECOLOGY, DERMATOLOGY, ENT_SPECIALIST }
}