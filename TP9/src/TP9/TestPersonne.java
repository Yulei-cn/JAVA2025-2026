package TP9;

import java.util.*;

public class TestPersonne {

    public static void main(String[] args) {

        // 5 objets, 3 personnes
        Affectation aff = new Affectation(5, 3);

        System.out.println("===== Protocole généré =====");
        for (Affectation.Pair<Personne, Integer> p : aff.getProtocole()) {
            System.out.println(
                p.getFirst().getNom() +
                " prend " + p.getSecond() + " objets"
            );
        }

        System.out.println("\n===== Préférences des personnes =====");
        for (Personne p : aff.getPersonnes()) {
            System.out.println(p);
        }

        System.out.println("\n===== Début de l'affectation =====");
        aff.affectation();

        System.out.println("\n===== Résultat final =====");
        for (Personne p : aff.getPersonnes()) {
            System.out.println(p.getNom() + " a obtenu : " + p.getObjetsObtenus());
        }
    }
}
