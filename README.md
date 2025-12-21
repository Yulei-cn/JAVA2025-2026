# README — Gestion des Budgets d’une Ville

## Projet Java-Objet — Licence 3 Informatique 2025–2026

Université Paris Dauphine – PSL

 

## 1. Objectif général

Ce projet simule :

- le fonctionnement d’une équipe municipale qui propose et évalue des projets  
  (sport, santé, culture, éducation, attractivité économique, etc.)
- l’optimisation budgétaire via le **problème du sac à dos multidimensionnel**
  (*Multidimensional Knapsack Problem*)

Deux grandes parties travaillent ensemble :

1. Simulation orientée objet (`package equipe`)
2. Optimisation du budget (`packages sacADos`, `solveur.glouton`, `solveur.hillclimbing`)

 

## 2. Simulation de l'équipe municipale

### Architecture objet (package `equipe/`)

equipe/
├── Personne.java (classe abstraite)
├── Elu.java
├── Evaluateur.java
├── Expert.java
├── Projet.java
├── Secteur.java (SPORT, SANTE, CULTURE, etc.)
├── TypeCout.java (ECONOMIQUE, SOCIAL, ENVIRONNEMENTAL)
├── EquipeMunicipale.java
└── package-info.java

 

### Fonctionnement d’un cycle de simulation

1. Les experts proposent des projets selon leur secteur
2. Les évaluateurs attribuent les coûts (économique, social, environnemental)
3. L’élu(e) attribue un bénéfice
4. Les projets sont stockés dans `projetsEtudies`

 

## 3. Sac à Dos Multidimensionnel (package `sacADos/`)

sacADos/
├── Objet.java
├── SacADos.java
├── VersSacADos.java
└── package-info.java

 

Chaque projet est converti en `Objet` :

- utilité = bénéfice du projet
- coûts = tableau d’entiers

Deux modes de génération sont disponibles :

1. Budgets selon les types de coûts (3 dimensions)
2. Budgets selon les secteurs (5 dimensions)

La classe `VersSacADos` assure la transformation automatique.

 

## 4. Solveurs d’optimisation

### Organisation des packages

solveur/
├── glouton/
│ ├── Comparateurs.java
│ ├── GloutonAjoutSolver.java
│ ├── GloutonRetraitSolver.java
│ └── package-info.java
│
└── hillclimbing/
├── HillClimbingSolver.java
└── package-info.java

 
 

### Solveurs gloutons

- **Glouton Ajout**  
  Ajout progressif des objets selon un critère (`f_somme`, `f_max`, etc.)

- **Glouton Retrait**  
  Retrait des objets les moins intéressants puis amélioration de la solution

 

## 5. Hill Climbing

La classe `HillClimbingSolver` implémente trois variantes :

### Hill Climbing standard

- voisinage déterministe
- paramètre `t = 1`
- arrêt sur optimum local

### Hill Climbing avec plateau

- autorise un nombre limité de mouvements à utilité égale
- paramètre `plateauMoves`

### Hill Climbing aléatoire

- génération aléatoire d’un nombre fixé de voisins
- paramètres configurables :
  - `t` (ajouts / retraits max)
  - `plateauMoves`
  - `nombreVoisins`

Cette version permet une exploration plus large de l’espace des solutions
et limite le blocage dans des optima locaux.

 

## 6. Classe Main — Menu interactif

main/
├── Main.java
└── package-info.java

 

Le menu interactif permet :

- d’afficher l’équipe municipale
- d’exécuter un cycle de simulation
- de générer une instance SacADos
- de tester les solveurs gloutons
- de tester les trois versions du Hill Climbing

Tous les sous-menus disposent d’une option **Retour**.

 

## 7. Tests unitaires — JUnit 5

Chaque classe importante possède une classe de test dédiée :

test/
├── sacADos/
│ ├── ObjetTest.java
│ └── SacADosTest.java
│
├── solveur/glouton/
│ ├── GloutonAjoutSolverTest.java
│ └── GloutonRetraitSolverTest.java
│
└── solveur/hillclimbing/
└── HillClimbingSolverTest.java

 

Les tests utilisent :

- `@BeforeEach`
- assertions `assertEquals`, `assertTrue`, etc.
- une convention de nommage claire :  
  `methodeTestee_Contexte_ResultatAttendu`

 

## 8. Documentation — Javadoc

- chaque package contient un fichier `package-info.java`
- classes et méthodes publiques documentées
- génération possible via Eclipse ou via l’outil `javadoc`

 

## 9. Compilation & Exécution

### Exécution depuis Eclipse

Run As → Java Application → main.Main

 

### Exécution des tests unitaires

Run As → JUnit Test

 

 

## 10. Remarques finales

- séparation claire entre simulation et optimisation
- usage des classes abstraites et des interfaces
- algorithmes paramétrables et comparables
- projet conforme aux exigences du cours Java-Objet