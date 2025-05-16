package com.xcelore.doctorpatient.service;

import com.xcelore.doctorpatient.dto.DoctorDto;
import com.xcelore.doctorpatient.entity.Doctor;
import com.xcelore.doctorpatient.entity.Patient;
import com.xcelore.doctorpatient.entity.Speciality;
import com.xcelore.doctorpatient.entity.Symptom;
import com.xcelore.doctorpatient.exception.ResourceNotFoundException;
import com.xcelore.doctorpatient.repository.DoctorRepository;
import com.xcelore.doctorpatient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Object suggestDoctors(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

        // Check if patient's city is one we serve
        try {
            Doctor.City.valueOf(patient.getCity().toUpperCase());
        } catch (IllegalArgumentException e) {
            return "We are still waiting to expand to your location";
        }

        Speciality requiredSpeciality = mapSymptomToSpeciality(patient.getSymptom());
        List<Doctor> doctors = doctorRepository.findByCityAndSpeciality(
                Doctor.City.valueOf(patient.getCity().toUpperCase()),
                requiredSpeciality
        );

        if (doctors.isEmpty()) {
            return "There isn't any doctor present at your location for your symptom";
        }

        return doctors.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Speciality mapSymptomToSpeciality(Symptom symptom) {
        return switch (symptom) {
            case ARTHRITIS, BACK_PAIN, TISSUE_INJURIES -> Speciality.ORTHOPAEDIC;
            case DYSMENORRHEA -> Speciality.GYNECOLOGY;
            case SKIN_INFECTION, SKIN_BURN -> Speciality.DERMATOLOGY;
            case EAR_PAIN -> Speciality.ENT_SPECIALIST;
        };
    }

    private DoctorDto convertToDto(Doctor doctor) {
        return new DoctorDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getCity().name(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                doctor.getSpeciality().name()
        );
    }
}