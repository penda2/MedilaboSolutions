package com.medilabo_solutions.patient_risk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo_solutions.patient_risk.model.RiskEvaluation;
import com.medilabo_solutions.patient_risk.services.RiskService;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

	private final RiskService riskService;

	public RiskController(RiskService riskService) {
		this.riskService = riskService;
	}

	@GetMapping("/evaluate/{patientId}")
	public ResponseEntity<RiskEvaluation> evaluateRisk(@PathVariable("patientId") String patientId) {
		RiskEvaluation riskEvaluation = riskService.evaluateRisk(patientId);
		return ResponseEntity.ok(riskEvaluation);
	}
}
