package main;

import java.util.Arrays;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;
import solveur.glouton.Comparateurs;
import solveur.glouton.GloutonAjoutSolver;
import solveur.glouton.GloutonRetraitSolver;

public class Main {

    public static void main(String[] args) {

        // === 1. Création des objets ===
        Objet o1 = new Objet(10, new int[]{4, 3, 2});
        Objet o2 = new Objet(8, new int[]{3, 3, 3});
        Objet o3 = new Objet(5, new int[]{1, 10, 4});
        Objet o4 = new Objet(7, new int[]{2, 2, 5});

        // === 2. Création du sac à dos ===
        SacADos sac = new SacADos(
                3,                      // dimension (3 coûts)
                new int[]{7, 7, 7},     // budgets max (eco, social, environ.)
                Arrays.asList(o1, o2, o3, o4)
        );

        // === 3. Test du Glouton Ajout ===
        GloutonAjoutSolver gAjout = new GloutonAjoutSolver();
        List<Objet> resultatAjout = gAjout.resoudre(sac, Comparateurs.f_somme());

        System.out.println("=== Résultat Glouton AJOUT ===");
        resultatAjout.forEach(System.out::println);


        // === 4. Test du Glouton Retrait ===
        GloutonRetraitSolver gRetrait = new GloutonRetraitSolver();
        List<Objet> resultatRetrait = gRetrait.resoudre(
                sac,
                Comparateurs.f_somme(),  // pour retirer d'abord les moins utiles
                Comparateurs.f_max()     // puis réessayer avec ajout
        );

        System.out.println("\n=== Résultat Glouton RETRAIT ===");
        resultatRetrait.forEach(System.out::println);
    }
}
