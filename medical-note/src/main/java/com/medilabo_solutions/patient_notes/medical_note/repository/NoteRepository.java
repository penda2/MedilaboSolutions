package com.medilabo_solutions.patient_notes.medical_note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo_solutions.patient_notes.medical_note.model.Notes;

// Le repository permettant d’interagir avec les collections MongoDB
public interface NoteRepository extends MongoRepository<Notes, String>{
	List<Notes> findByPatientId(String patientId);

}
