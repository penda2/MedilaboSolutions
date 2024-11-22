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
import org.springframework.web.client.RestTemplate;

import com.medilabo_solutions.FrontEnd.models.Patient;

@Controller
@RequestMapping("/patients")
public class FrontendController {

	private final RestTemplate restTemplate;

	public FrontendController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

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

	@PostMapping("/update")
	public String updatePatient(@ModelAttribute Patient patient) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user1", "password1");
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);

		restTemplate.exchange("http://localhost:8080/api/patients/" + patient.getId(), HttpMethod.PUT, entity,
				Void.class);

		return "redirect:/patients";
	}

}
