package com.medilabo_solutions.patient_notes.medical_note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo_solutions.patient_notes.medical_note.model.Notes;
import com.medilabo_solutions.patient_notes.medical_note.services.NotesService;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	private final NotesService notesService;

	// Injection de dépendance
	@Autowired
	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}
	
	// Créer une note
	@PostMapping
	public ResponseEntity<Notes> createNote(@RequestBody Notes notes) {
		Notes savedNote = notesService.saveNote(notes);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
	}

	// Récupère les notes par l'id du patient
	 @GetMapping("/patient/{patientId}")
	    public ResponseEntity<List<Notes>> getNotesByPatientId(@PathVariable String patientId) {
	        List<Notes> notes = notesService.findNotesByPatientId(patientId);
	        return ResponseEntity.ok(notes);
	    }
	// 	// Récupère la liste des notes 
	@GetMapping
	public ResponseEntity<List<Notes>> getAllNotes() {
	    List<Notes> notes = notesService.findAllNotes();
	    return ResponseEntity.ok(notes);
	}	
}
