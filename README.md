
# Medilabo Solutions - Microservices Application

L'application de pratiques de "Green Code" peut contribuer à réduire l'empreinte carbone des applications.



## Liste des recommandations d’amélioration pour ce projet:

- Optimisation du Code
Réduction de la complexité des algorithmes et éviter les calculs redondants . Par exemple, optimiser les requêtes de recherche pour qu'elles soient plus efficaces.

Nettoyer le code pour favoriser l’accessibilité , par exemple soigner l'enchaînement des balises HTML en plaçant correctement les balises, comme les titres H1, H2 ou H3 pour les outils d’audiodescription.

- Gestion des Ressources
Gestion de la mémoire : s'assurer que les micro services  PatientService ou NoteService gèrent efficacement la mémoire en fermant les connexions à la base de données après utilisation.

- Utilisation Efficace des Conteneurs
Minimiser la taille des conteneurs Docker : Utiliser des images Docker optimisées pour medical-note-db et medical-note en supprimant les dépendances inutiles.
