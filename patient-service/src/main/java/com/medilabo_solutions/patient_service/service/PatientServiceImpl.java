package com.medilabo_solutions.patient_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo_solutions.patient_service.model.Patient;
import com.medilabo_solutions.patient_service.repository.PatientRepository;

// Classe utilisant le repository pour interagir avec la base de données
@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public Optional<Patient> getPatientById(Integer id) {
		return patientRepository.findById(id);
	}

	@Override
	public Patient updatePatient(Integer id, Patient updatedPatient) {
		return patientRepository.findById(id).map(existingPatient -> {
			existingPatient.setPrenom(
					updatedPatient.getPrenom() != null ? updatedPatient.getPrenom() : existingPatient.getPrenom());
			existingPatient
					.setNom(updatedPatient.getNom() != null ? updatedPatient.getNom() : existingPatient.getNom());
			existingPatient.setDateDeNaissance(
					updatedPatient.getDateDeNaissance() != null ? updatedPatient.getDateDeNaissance()
							: existingPatient.getDateDeNaissance());
			existingPatient.setGenre(
					updatedPatient.getGenre() != null ? updatedPatient.getGenre() : existingPatient.getGenre());
			existingPatient.setAdresse(
					updatedPatient.getAdresse() != null ? updatedPatient.getAdresse() : existingPatient.getAdresse());
			existingPatient.setTelephone(updatedPatient.getTelephone() != null ? updatedPatient.getTelephone()
					: existingPatient.getTelephone());

			return patientRepository.save(existingPatient);
		}).orElseThrow(() -> new com.medilabo_solutions.patient_service.exceptions.ResourceNotFoundException(
				"Patient avec l'id " + id + " n'existe pas."));
	}
}
