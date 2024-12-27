package com.medilabo_solutions.FrontEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.medilabo_solutions.FrontEnd.models.Notes;
import com.medilabo_solutions.FrontEnd.models.Patient;
import com.medilabo_solutions.FrontEnd.models.RiskEvaluation;
import com.medilabo_solutions.FrontEnd.service.PatientService;

@Controller
@RequestMapping("/patients")
public class FrontendController {

	@Autowired
	private PatientService patientService;

	// récupère la liste des patients
	@GetMapping
	public String getPatients(Model model) {
		try {
			List<Patient> patients = patientService.getAllPatients();
			model.addAttribute("patients", patients);
			return "patients";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer les patients.");
			return "error";
		}
	}

	// Affiche la page de modification d'un patient
	@GetMapping("/edit/{id}")
	public String editPatient(@PathVariable("id") String id, Model model) {
		try {
			Patient patient = patientService.getPatientById(id);
			model.addAttribute("patient", patient);
			return "edit-patient";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer le patient.");
			return "error";
		}
	}

	// Méthode post pour modifier les informations d'un patient
	@PostMapping("/edit")
	public String updatePatient(@ModelAttribute Patient patient, Model model) {
		try {
			patientService.updatePatient(patient);
			model.addAttribute("successMessage", "Informations du patient mises à jour avec succès !");
			return "edit-patient";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "Erreur lors de la mise à jour du patient.");
			return "/patients/edit/" + patient.getId();
		}
	}

	// Afficher les informations d'un patient + notes et niveau de risque
	@GetMapping("/details/{id}")
	public String viewPatientDetails(@PathVariable("id") String id, Model model) {
		try {
			Patient patient = patientService.getPatientById(id);
			List<Notes> notes = patientService.getNotesByPatientId(id);
			RiskEvaluation riskEvaluation = patientService.getRiskEvaluation(id);

			model.addAttribute("patient", patient);
			model.addAttribute("notes", notes);
			model.addAttribute("riskEvaluation", riskEvaluation);

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
			List<Notes> notes = patientService.getAllNotes();
			model.addAttribute("notes", notes);
			return "notes";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer les notes.");
			return "error";
		}
	}

	// Affiche le formulaire d'ajout de notes
	@GetMapping("/addNote")
	public String showAddNoteForm(@RequestParam("patientId") String patientId, Model model) {
		try {
			Patient patient = patientService.getPatientById(patientId);
			model.addAttribute("patient", patient);
			return "addNote";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Erreur lors du chargement du patient.");
			return "redirect:/patients";
		}
	}

	// Ajout d'une nouvelle note
	@PostMapping("/addNote")
	public String addNote(@RequestParam("patientId") String patientId, @ModelAttribute Notes note, Model model) {
		try {
			patientService.addNoteToPatient(patientId, note);
			model.addAttribute("successMessage", "Note ajoutée avec succès !");
		} catch (Exception e) {
			e.printStackTrace();
			Patient patient = patientService.getPatientById(patientId);
			model.addAttribute("patient", patient);
			model.addAttribute("errorMessage", "Erreur lors de l'ajout de la note : " + e.getMessage());
			return "addNote";
		}
		Patient patient = patientService.getPatientById(patientId);
		model.addAttribute("patient", patient);
		return "addNote";
	}

	// Affiche toutes les notes d'un patient
	@GetMapping("/historique/{id}")
	public String getPatientHistorique(@PathVariable("id") String patientId, Model model) {
		try {
			Patient patient = patientService.getPatientById(patientId);
			List<Notes> notes = patientService.getNotesByPatientId(patientId);

			model.addAttribute("patient", patient);
			model.addAttribute("notes", notes);
			return "historique";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Impossible de récupérer l'historique.");
			return "error";
		}
	}
}
