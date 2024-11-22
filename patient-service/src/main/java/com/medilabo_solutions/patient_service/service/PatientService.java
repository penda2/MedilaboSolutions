package com.medilabo_solutions.patient_service.service;

import java.util.List;
import java.util.Optional;

import com.medilabo_solutions.patient_service.model.Patient;

public interface PatientService {

	Patient savePatient(Patient patient);
	List<Patient> getAllPatients();
	Optional<Patient> getPatientById(String id);
	Patient updatePatient(String id, Patient updatedPatient);
}