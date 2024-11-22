package com.medilabo_solutions.patient_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo_solutions.patient_service.exceptions.ResourceNotFoundException;
import com.medilabo_solutions.patient_service.model.Patient;
import com.medilabo_solutions.patient_service.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		Patient savedPatient = patientService.savePatient(patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
		return patientService.getPatientById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient updatedPatient) {
		try {
			Patient patient = patientService.updatePatient(id, updatedPatient);
			return ResponseEntity.ok(patient);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
