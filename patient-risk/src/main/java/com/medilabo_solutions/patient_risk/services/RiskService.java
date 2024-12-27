package com.medilabo_solutions.patient_risk.services;

import java.util.Base64;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.medilabo_solutions.patient_risk.model.Notes;
import com.medilabo_solutions.patient_risk.model.Patient;
import com.medilabo_solutions.patient_risk.model.RiskEvaluation;

@Service
public class RiskService {

	private final RestTemplate restTemplate;

	public RiskService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Patient fetchPatient(String patientId) {
		HttpHeaders headers = new HttpHeaders();
		String auth = "user1:password1";
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
		headers.set("Authorization", "Basic " + encodedAuth);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Patient> response = restTemplate.exchange("http://gateway:8080/api/patients/" + patientId,
					HttpMethod.GET, entity, Patient.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Erreur lors de la récupération du patient : " + e.getMessage());
		}
	}

	public List<Notes> fetchNotes(String patientId) {
		HttpHeaders headers = new HttpHeaders();
		String auth = "user1:password1";
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
		headers.set("Authorization", "Basic " + encodedAuth);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<List<Notes>> response = restTemplate.exchange("http://gateway:8080/api/notes/patient/" + patientId, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Notes>>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Erreur lors de la récupération des notes : " + e.getMessage());
		}
	}

	public int countTriggerTerms(List<Notes> notes) {
		List<String> triggerTerms = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
				"Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps");
		int count = 0;
		for (Notes note : notes) {
			for (String term : triggerTerms) {
				if (note.getNoteContent().toLowerCase().contains(term.toLowerCase())) {
					count++;
				}
			}
		}
		return count;
	}

	public String determineRisqueLevel(Patient patient, int countTriggerTerms) {
		int age = patient.getAge();
		String gender = patient.getGenre();

		if (countTriggerTerms < 2) {
			return "None";
		} else if (countTriggerTerms >= 2 && countTriggerTerms <= 5 && age >= 30) {
			return "Borderline";
		} else if ((gender.equalsIgnoreCase("M") && age < 30 && countTriggerTerms >= 3)
				|| (gender.equalsIgnoreCase("F") && age < 30 && countTriggerTerms == 4)
				|| (age >= 30 && countTriggerTerms >= 6 && countTriggerTerms <= 7)) {
			return "InDanger";
		} else if ((gender.equalsIgnoreCase("M") && age < 30 && countTriggerTerms >= 5)
				|| (gender.equalsIgnoreCase("F") && age < 30 && countTriggerTerms >= 7)
				|| (age >= 30 && countTriggerTerms >= 8)) {
			return "EarlyOnset";
		}

		return "Non évalué.";
	}

	public RiskEvaluation evaluateRisk(String patientId) {
		Patient patient = fetchPatient(patientId);
		if (patient == null) {
			throw new IllegalArgumentException("Patient introuvable");
		}

		List<Notes> notes = fetchNotes(patientId);
		if (notes == null) {
			throw new IllegalArgumentException("Notes introuvables");
		}

		int countTriggers = countTriggerTerms(notes);

		String risqueLevel = determineRisqueLevel(patient, countTriggers);

		return new RiskEvaluation(patient.getId(), patient.getNom(), patient.getPrenom(), patient.getAge(),
				risqueLevel);
	}
}
