package com.medilabo_solutions.patient_service.service;

import java.util.List;
import java.util.Optional;

import com.medilabo_solutions.patient_service.model.Patient;

//interface définissant les méthodes disponibles pour les opérations liées à l'entité
public interface PatientService {

	Patient savePatient(Patient patient);
	
	List<Patient> getAllPatients();
	
	Optional<Patient> getPatientById(Integer id);
	
	Patient updatePatient(Integer id, Patient updatedPatient);
}
