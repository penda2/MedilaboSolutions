package com.medilabo_solutions.FrontEnd.models;

// Entité définie pour correspondre à la structure existante dans le micro service note.
public class Notes {
	
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
