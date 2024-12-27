package com.medilabo_solutions.patient_notes.medical_note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Entité stockée dans une collection de la base de données (MongoDB)
@Document(collection = "notes")
public class Notes {
	@Id
	private String id;
	private String patientId;
	private String noteContent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
}
