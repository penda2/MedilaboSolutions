DELETE FROM patient;

ALTER TABLE patient AUTO_INCREMENT = 1;

INSERT INTO patient (prenom, nom, date_de_naissance, genre, adresse,telephone)VALUES 
('Test', 'TestNone', '1966-12-31','F','1 Brookside St', '100-222-3333'),
('Test', 'TestBorderline', '1945-06-24','M','2 High St', '200-333-4444'),
('Test', 'TestInDanger', '2004-06-18','M','3 Club Road', '300-444-5555'),
('Test', 'TestEarlyOnset', '2002-06-28','F','4 Valley Dr', '400-555-6666');