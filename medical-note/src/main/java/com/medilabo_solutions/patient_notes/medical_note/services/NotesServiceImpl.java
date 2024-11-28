package com.medilabo_solutions.patient_notes.medical_note.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo_solutions.patient_notes.medical_note.model.Notes;
import com.medilabo_solutions.patient_notes.medical_note.repository.NoteRepository;

@Service
public class NotesServiceImpl implements NotesService {

	private final NoteRepository noteRepository;

	@Autowired
	public NotesServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@Override
	public List<Notes> findNotesByPatientId(String patientId) {
		return noteRepository.findByPatientId(patientId);
	}

	@Override
	public Notes saveNote(Notes notes) {
		return noteRepository.save(notes);
	}

	@Override
	public List<Notes> findAllNotes() {
		return noteRepository.findAll();
	}

}
