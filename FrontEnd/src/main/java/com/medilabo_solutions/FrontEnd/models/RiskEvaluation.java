package com.medilabo_solutions.FrontEnd.models;

// Entité définie pour correspondre à la structure existante dans le micro service des risques.
public class RiskEvaluation {
	
	private String patientId;
	private String nom;
	private String prenom;
	private int age;
	private String risqueLevel;

	public RiskEvaluation(String patientId, String nom, String prenom, int age, String risqueLevel) {
		this.patientId = patientId;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.risqueLevel = risqueLevel;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRisqueLevel() {
		return risqueLevel;
	}

	public void setRisqueLevel(String risqueLevel) {
		this.risqueLevel = risqueLevel;
	}

}
