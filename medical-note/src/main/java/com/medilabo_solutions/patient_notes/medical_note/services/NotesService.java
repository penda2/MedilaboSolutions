package com.medilabo_solutions.patient_notes.medical_note.services;

import java.util.List;

import com.medilabo_solutions.patient_notes.medical_note.model.Notes;

// interface définissant les méthodes disponibles pour les opérations liées à l'entité
public interface NotesService {
    List<Notes> findNotesByPatientId(String patientId);

	Notes saveNote(Notes notes);

	List<Notes> findAllNotes();

}
