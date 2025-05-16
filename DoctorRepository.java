package com.xcelore.doctorpatient.repository;

import com.xcelore.doctorpatient.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByCityAndSpeciality(Doctor.City city, Doctor.Speciality speciality);
}