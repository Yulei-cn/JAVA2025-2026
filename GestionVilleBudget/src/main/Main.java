package main;

import java.util.Arrays;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;
import solveur.glouton.Comparateurs;
import solveur.glouton.GloutonAjoutSolver;
import solveur.glouton.GloutonRetraitSolver;
import solveur.hillclimbing.HillClimbingSolver;

public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("     Test des solveurs (Glouton + HC)");
        System.out.println("==============================================\n");

        // === 1. Création des objets ===
        Objet o1 = new Objet(10, new int[]{4, 3, 2});
        Objet o2 = new Objet(8,  new int[]{3, 3, 3});
        Objet o3 = new Objet(5,  new int[]{1, 10, 4});
        Objet o4 = new Objet(7,  new int[]{2, 2, 5});

        // === 2. Création du sac à dos ===
        SacADos sac = new SacADos(
                3,                      // dimension
                new int[]{7, 7, 7},     // budgets
                Arrays.asList(o1, o2, o3, o4)
        );

        System.out.println("Instance du sac à dos :");
        for (Objet o : sac.getObjets()) {
            System.out.println("  - " + o);
        }
        System.out.println("\nBudgets : " + Arrays.toString(sac.getBudgets()) + "\n");


        // === 3. Glouton Ajout ===
        System.out.println("===== Solveur Glouton AJOUT (critère : somme) =====");
        GloutonAjoutSolver gAjout = new GloutonAjoutSolver();
        List<Objet> resAjout = gAjout.resoudre(sac, Comparateurs.f_somme());

        afficherSolution("Glouton Ajout", resAjout, sac);


        // === 4. Glouton Retrait ===
        System.out.println("\n===== Solveur Glouton RETRAIT =====");
        GloutonRetraitSolver gRetrait = new GloutonRetraitSolver();
        List<Objet> resRetrait = gRetrait.resoudre(
                sac,
                Comparateurs.f_somme(),   // critère pour retirer
                Comparateurs.f_max()      // critère pour ajout
        );

        afficherSolution("Glouton Retrait", resRetrait, sac);


        // === 5. Hill Climbing (t = 1) ===
        System.out.println("\n===== Hill Climbing (t = 1) =====");
        HillClimbingSolver hc = new HillClimbingSolver();
        List<Objet> resHC = hc.resoudre(sac, resAjout, 1, 0);

        afficherSolution("Hill Climbing (t=1)", resHC, sac);


        // === 6. Hill Climbing (t = 1 + plateaux) ===
        System.out.println("\n===== Hill Climbing (t = 1, plateaux = 3) =====");
        List<Objet> resHC2 = hc.resoudre(sac, resAjout, 1, 3);

        afficherSolution("Hill Climbing (t=1, plateau=3)", resHC2, sac);


        System.out.println("\n========== FIN DES TESTS ==========\n");
    }


    /** Affichage propre des solutions */
    private static void afficherSolution(String titre, List<Objet> sol, SacADos sac) {
        System.out.println("\n--- " + titre + " ---");
        for (Objet o : sol) {
            System.out.println("  * " + o);
        }
        System.out.println("Utilité totale = " + sac.utiliteTotale(sol));
    }
}
