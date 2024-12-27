package com.medilabo_solutions.FrontEnd.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medilabo_solutions.FrontEnd.models.Notes;
import com.medilabo_solutions.FrontEnd.models.Patient;
import com.medilabo_solutions.FrontEnd.models.RiskEvaluation;


//Service front regroupant toutes les méthodes nécessaires pour interagir avec les autres microservices
@Service
public class PatientService {

	@Autowired
	private RestTemplate restTemplate;

	private final String authHeader;

	// Initialise l'en-tête d'authentification de base
	public PatientService() {
		String auth = "user1:password1";
		this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
	}

	// Méthode qui encapsule la logique de création et de configuration des en-têtes HTTP nécessaires pour les appels via RestTemplate
	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		return headers;
	}

	// Récupère la liste des patients
	public List<Patient> getAllPatients() {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		ResponseEntity<List<Patient>> response = restTemplate.exchange("http://gateway:8080/api/patients",
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<Patient>>() {
				});
		return response.getBody();
	}

	// Récupérer un patient par son Id
	public Patient getPatientById(String id) {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		ResponseEntity<Patient> response = restTemplate.exchange("http://gateway:8080/api/patients/" + id,
				HttpMethod.GET, entity, Patient.class);
		return response.getBody();
	}

	// Modifier les informations d'un patient 
	public void updatePatient(Patient patient) {
		HttpHeaders headers = createHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);
		restTemplate.exchange("http://gateway:8080/api/patients/" + patient.getId(), HttpMethod.PUT, entity,
				Void.class);
	}

	// Récupère la liste des notes 
	public List<Notes> getAllNotes() {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		ResponseEntity<List<Notes>> response = restTemplate.exchange("http://gateway:8080/api/notes", HttpMethod.GET,
				entity, new ParameterizedTypeReference<List<Notes>>() {
				});
		return response.getBody();
	}

	// Récupère la liste des notes avec l'Id des patients
	public List<Notes> getNotesByPatientId(String patientId) {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		ResponseEntity<List<Notes>> response = restTemplate.exchange(
				"http://gateway:8080/api/notes/patient/" + patientId, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Notes>>() {
				});
		return response.getBody();
	}

	// Ajouter une note 
	public void addNoteToPatient(String patientId, Notes note) {
		HttpHeaders headers = createHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		note.setPatientId(patientId);
		HttpEntity<Notes> entity = new HttpEntity<>(note, headers);
		restTemplate.postForEntity("http://gateway:8080/api/notes", entity, Notes.class);
	}

	// obtenir l'évaluation des risques 
	public RiskEvaluation getRiskEvaluation(String patientId) {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		ResponseEntity<RiskEvaluation> response = restTemplate.exchange(
				"http://gateway:8080/api/risk/evaluate/" + patientId, HttpMethod.GET, entity, RiskEvaluation.class);
		return response.getBody();
	}
}
