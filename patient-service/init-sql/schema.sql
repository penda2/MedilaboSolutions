CREATE SCHEMA IF NOT EXISTS `patientdb`;
USE `patientdb` ;

CREATE TABLE IF NOT EXISTS patient (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    prenom VARCHAR(255) NOT NULL, 
    nom VARCHAR(255) NOT NULL,  
    date_de_naissance DATE NOT NULL, 
    genre VARCHAR(255) NOT NULL, 
    adresse VARCHAR(255), 
    telephone VARCHAR(255)
);
