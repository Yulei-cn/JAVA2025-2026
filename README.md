# README — Gestion des Budgets d’une Ville

## Projet Java-Objet — Licence 3 Informatique 2025–2026

Université Paris Dauphine – PSL

---

## 1. Objectif général

Ce projet simule :

* le fonctionnement d’une équipe municipale qui propose et évalue des projets
  (sport, santé, culture, éducation, attractivité économique, etc.)
* l’optimisation budgétaire via le problème du sac à dos multidimensionnel
  (*Multidimensional Knapsack Problem*)

Deux grandes parties travaillent ensemble :

1. Simulation orientée objet (`package equipe`)
2. Optimisation du budget (`packages sacADos`, `solveur.glouton`, `solveur.hillclimbing`)

---

## 2. Simulation de l'équipe municipale

### Architecture objet (package `equipe/`)

```
equipe/
 ├── Personne.java          (classe abstraite)
 ├── Elu.java               (évalue le bénéfice)
 ├── Evaluateur.java        (évalue les coûts)
 ├── Expert.java            (propose des projets)
 ├── package_info.java
 ├── Projet.java
 ├── Secteur.java           (énumération : SPORT, SANTE, etc.)
 ├── TypeCout.java          (3 types de coûts)
 └── EquipeMunicipale.java
```

### Fonctionnement d’un cycle de simulation

1. Les experts proposent des projets selon leur secteur
2. Les évaluateurs attribuent les coûts (économique, social, environnemental)
3. L’élu(e) attribue un bénéfice
4. Le projet est stocké dans `projetsEtudies`

---

## 3. Sac à Dos Multidimensionnel (package `sacADos/`)

```
sacADos/
 ├── Objet.java
 ├── SacADos.java
 ├── VersSacADos.java
 └── package_info.java
```

Chaque projet est converti en `Objet` :

* utilité = bénéfice
* coûts = tableau d’entiers

Deux modes :

1. Budgets selon coûts (3 dimensions)
2. Budgets selon secteurs (5 dimensions)

`VersSacADos` permet la transformation automatique.

---

## 4. Solveurs d’optimisation

### Packages

```
solveur/
 ├── glouton/
 │     ├── Comparateurs.java
 │     ├── GloutonAjoutSolver.java
 │     ├── GloutonRetraitSolver.java
 │     └── package_info.java
 │
 └── hillclimbing/
       ├── HillClimbingSolver.java
       └── package_info.java
```

### Méthodes implémentées

* **Glouton Ajout**
  Ajout des objets triés selon un critère (`f_somme`, `f_max`, etc.)

* **Glouton Retrait**
  Retrait progressif des objets les moins utiles, puis amélioration gloutonne

* **Hill Climbing**
  Recherche locale à partir d’une solution gloutonne

Paramètres configurables :

* `t` : taille du voisinage
* `plateauMoves` : nombre de mouvements neutres autorisés

---

## 5. Classe Main — Menu interactif

```
main/
 ├── Main.java
 └── TestAll.java
```

### Main.java

Fonctionnalités :

* lancer un cycle de simulation
* générer une instance SacADos
* choisir un algorithme de résolution
* afficher la sélection et l’utilité totale

### TestAll.java

Permet d'exécuter les tests unitaires essentiels :

1. Glouton Ajout — `f_somme`
2. Glouton Ajout — `f_max`
3. Glouton Retrait
4. Hill Climbing (t = 1)
5. Hill Climbing (t = 1, plateau = 3)

---

## 6. Structure complète du projet

```
src/
 ├── equipe/
 ├── main/
 ├── sacADos/
 ├── solveur/
 └── test/
```

> Le fichier `module-info.java` a été retiré afin de permettre l'utilisation correcte de JUnit 5 sous Eclipse.

---

## 7. Compilation & Exécution

### Compilation en ligne de commande

```bash
javac -d bin $(find src -name "*.java")
```

### Exécution

```bash
java -cp bin main.Main
```

---

## 8. Tests unitaires — JUnit 5

Tous les tests sont exécutables via Eclipse :

```
Right click on any *Test.java → Run As → JUnit Test
```

Éléments testés :

* admissibilité des solutions
* utilité totale
* performance des solveurs gloutons
* amélioration via Hill Climbing

