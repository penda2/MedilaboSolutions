package com.medilabo_solutions.FrontEnd.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo_solutions.FrontEnd.models.Notes;
import com.medilabo_solutions.FrontEnd.models.Patient;

@Controller
@RequestMapping("/patients")
public class FrontendController {

	private final RestTemplate restTemplate;

	public FrontendController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// récupère la liste des patients
	@GetMapping
	public String getPatients(Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			String auth = "user1:password1";
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			headers.set("Authorization", "Basic " + encodedAuth);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<List<Patient>> response = restTemplate.exchange("http://localhost:8080/api/patients",
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<Patient>>() {
					});

			List<Patient> patients = response.getBody();
			model.addAttribute("patients", patients);

			return "patients";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer les patients.");
			return "error";
		}
	}

	// Récupère le patient par son identifiant 
	@GetMapping("/{id}")
	public String getPatientById(@PathVariable("id") String id, Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			String auth = "user1:password1";
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			headers.set("Authorization", "Basic " + encodedAuth);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<Patient> response = restTemplate.exchange("http://localhost:8080/api/patients/" + id,
					HttpMethod.GET, entity, Patient.class);

			model.addAttribute("patient", response.getBody());
			return "patient-detail";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer les informations du patient.");
			return "error";
		}
	}

	// Affiche la page de modification d'un patient 
	@GetMapping("/edit/{id}")
	public String editPatient(@PathVariable("id") String id, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1", "password1");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Patient> response = restTemplate.exchange("http://localhost:8080/api/patients/" + id,
				HttpMethod.GET, entity, Patient.class);

		model.addAttribute("patient", response.getBody());
		return "edit-patient";
	}

	// Méthode post pour modifier un patient 
	@PostMapping("/edit")
	public String updatePatient(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("user1", "password1");
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);

			restTemplate.exchange("http://localhost:8080/api/patients/" + patient.getId(), HttpMethod.PUT, entity,
					Void.class);

			redirectAttributes.addFlashAttribute("successMessage", "Informations du patient mis à jour avec succès !");
			return "redirect:/patients";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour du patient.");
			return "redirect:/patients/edit/" + patient.getId();
		}
	}

	// Afficher les informations d'un patient
	@GetMapping("/details/{id}")
	public String viewPatientDetails(@PathVariable("id") String id, Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("user1", "password1");

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<Patient> patientResponse = restTemplate.exchange("http://localhost:8080/api/patients/" + id,
					HttpMethod.GET, entity, Patient.class);
			model.addAttribute("patient", patientResponse.getBody());

			ResponseEntity<List<Notes>> notesResponse = restTemplate.exchange(
					"http://localhost:8080/api/notes/patient/" + id, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Notes>>() {
					});
			model.addAttribute("notes", notesResponse.getBody());

			return "patient-details";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "Impossible de récupérer les informations du patient.");
			return "error";
		}
	}

	// Affiche la liste des notes des patients 
	@GetMapping("/notes")
	public String getNotes(Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			String auth = "user1:password1";
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			headers.set("Authorization", "Basic " + encodedAuth);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<List<Notes>> response = restTemplate.exchange("http://localhost:8080/api/notes",
					HttpMethod.GET, entity, new ParameterizedTypeReference<List<Notes>>() {
					});

			List<Notes> notes = response.getBody();

			model.addAttribute("notes", notes);
			return "notes";
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			model.addAttribute("error", "Erreur HTTP : " + e.getStatusCode());
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
			model.addAttribute("error", "Erreur serveur : " + e.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer les notes.");
		}
		return "error";
	}

	// Affiche le formulaire d'ajout de notes 
	@GetMapping("/addNote")
	public String showAddNoteForm(@RequestParam("patientId") String patientId, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1", "password1");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Patient> response = restTemplate.exchange("http://localhost:8080/api/patients/" + patientId,
				HttpMethod.GET, entity, Patient.class);

		model.addAttribute("patient", response.getBody());
		model.addAttribute("note", new Notes());
		return "addNote";
	}

	// Ajout d'une nouvelle note 
	@PostMapping("/addNote")
	public String addNote(@RequestParam("patientId") String patientId, @ModelAttribute Notes note, Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("user1", "password1");
			headers.setContentType(MediaType.APPLICATION_JSON);

			note.setPatientId(patientId);

			HttpEntity<Notes> entity = new HttpEntity<>(note, headers);

			restTemplate.postForEntity("http://localhost:8083/api/notes", entity, Notes.class);

			return "redirect:/patients";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible d'ajouter la note.");
			return "error";
		}
	}

	// Affiche les infortions d'un patient et ses notes 
	@GetMapping("/historique/{id}")
	public String getPatientHistorique(@PathVariable("id") String patientId, Model model) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("user1", "password1");
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<Patient> patientResponse = restTemplate
					.exchange("http://localhost:8080/api/patients/" + patientId, HttpMethod.GET, entity, Patient.class);

			ResponseEntity<List<Notes>> notesResponse = restTemplate.exchange(
					"http://localhost:8080/api/notes/patient/" + patientId, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Notes>>() {
					});

			model.addAttribute("patient", patientResponse.getBody());
			model.addAttribute("notes", notesResponse.getBody());

			return "historique";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer l'historique.");
			return "error";
		}
	}
}
