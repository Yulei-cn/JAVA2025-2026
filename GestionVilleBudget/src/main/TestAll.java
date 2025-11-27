package main;

import java.util.Arrays;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;
import solveur.glouton.Comparateurs;
import solveur.glouton.GloutonAjoutSolver;
import solveur.glouton.GloutonRetraitSolver;
import solveur.hillclimbing.HillClimbingSolver;

public class TestAll {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("   TEST OFFICIEL — 5 SOLVEURS EXIGÉS PAR LE TP");
        System.out.println("==============================================\n");

        // ---------------------------
        // 1. Création des objets
        // ---------------------------
        Objet o1 = new Objet(10, new int[]{4, 3, 2});
        Objet o2 = new Objet(8,  new int[]{3, 3, 3});
        Objet o3 = new Objet(5,  new int[]{1, 10, 4});
        Objet o4 = new Objet(7,  new int[]{2, 2, 5});

        SacADos sac = new SacADos(
                3,
                new int[]{7, 7, 7},
                Arrays.asList(o1, o2, o3, o4)
        );

        System.out.println("Instance du sac-à-dos :");
        sac.getObjets().forEach(o -> System.out.println("  - " + o));
        System.out.println("\nBudgets = " + Arrays.toString(sac.getBudgets()) + "\n");


        // ----------------------------------------
        // TEST 1 — Glouton Ajout (f_somme)
        // ----------------------------------------
        System.out.println("===== TEST 1 : Glouton Ajout (critère f_somme) =====");
        GloutonAjoutSolver gAjout1 = new GloutonAjoutSolver();
        List<Objet> sol1 = gAjout1.resoudre(sac, Comparateurs.f_somme());
        afficher("Glouton Ajout — f_somme", sol1, sac);


        // ----------------------------------------
        // TEST 2 — Glouton Ajout (f_max)
        // ----------------------------------------
        System.out.println("\n===== TEST 2 : Glouton Ajout (critère f_max) =====");
        GloutonAjoutSolver gAjout2 = new GloutonAjoutSolver();
        List<Objet> sol2 = gAjout2.resoudre(sac, Comparateurs.f_max());
        afficher("Glouton Ajout — f_max", sol2, sac);


        // ----------------------------------------
        // TEST 3 — Glouton Retrait
        // ----------------------------------------
        System.out.println("\n===== TEST 3 : Glouton Retrait =====");
        GloutonRetraitSolver gRetrait = new GloutonRetraitSolver();
        List<Objet> sol3 = gRetrait.resoudre(
                sac,
                Comparateurs.f_somme(),   // enlever les moins bons
                Comparateurs.f_max()      // puis ajout
        );
        afficher("Glouton Retrait", sol3, sac);


        // ----------------------------------------
        // TEST 4 — Hill Climbing (t = 1)
        // ----------------------------------------
        System.out.println("\n===== TEST 4 : Hill Climbing (t = 1, sans plateau) =====");
        HillClimbingSolver hc = new HillClimbingSolver();
        List<Objet> sol4 = hc.resoudre(sac, sol1, 1, 0);
        afficher("Hill Climbing (t = 1)", sol4, sac);


        // ----------------------------------------
        // TEST 5 — Hill Climbing (t = 1, plateauMoves = 3)
        // ----------------------------------------
        System.out.println("\n===== TEST 5 : Hill Climbing (t = 1, plateau = 3) =====");
        List<Objet> sol5 = hc.resoudre(sac, sol1, 1, 3);
        afficher("Hill Climbing (t = 1, plateau=3)", sol5, sac);


        System.out.println("\n========== FIN DES 5 TESTS OBLIGATOIRES ==========\n");
    }


    /** Format d'affichage standardisé */
    private static void afficher(String titre, List<Objet> sol, SacADos sac) {
        System.out.println("\n--- " + titre + " ---");
        sol.forEach(o -> System.out.println("  * " + o));
        System.out.println("Utilité totale = " + sac.utiliteTotale(sol));
    }
}
