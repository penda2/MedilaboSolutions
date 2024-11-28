package com.medilabo_solutions.FrontEnd.models;

import java.time.LocalDate;
import java.time.Period;

public class Patient {

	private String id;
	private String prenom;
	private String nom;
	private LocalDate dateDeNaissance;
	private String genre;
	private String adresse;
	private String telephone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public int getAge() {
        if (this.dateDeNaissance != null) {
            return Period.between(this.dateDeNaissance, LocalDate.now()).getYears();
        }
        return 0;
    }
}
