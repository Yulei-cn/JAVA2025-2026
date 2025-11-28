2. Simulation de l'équipe municipale
Architecture objet (package equipe/)
equipe/
 ├── Personne.java          (classe abstraite)
 ├── Elu.java               (évalue le bénéfice)
 ├── Evaluateur.java        (évalue les coûts)
 ├── EvaluateurRole.java    (interface)
 ├── Expert.java            (propose des projets)
 ├── Projet.java
 ├── Secteur.java           (énumération : SPORT, SANTE, etc.)
 ├── TypeCout.java          (3 types de coûts)
 └── EquipeMunicipale.java