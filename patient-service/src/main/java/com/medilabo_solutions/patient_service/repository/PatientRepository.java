package com.medilabo_solutions.patient_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo_solutions.patient_service.model.Patient;

// Repository de gestion des interactions avec la base de données (MySql)
@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer >{

}
