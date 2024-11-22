package com.medilabo_solutions.patient_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo_solutions.patient_service.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String>{

}
