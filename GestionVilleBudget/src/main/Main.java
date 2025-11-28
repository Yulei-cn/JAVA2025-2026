package main;

import java.util.*;
import equipe.*;
import sacADos.*;
import solveur.glouton.*;
import solveur.hillclimbing.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        EquipeMunicipale equipe = construireEquipeDemonstration();  // 创建默认团队
        SacADos instanceSac = null;  // 用于后续 optimization

        while (true) {

            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ");


            switch (choix) {

                case 1:
                    equipe.afficherEquipe();
                    break;

                case 2:
                    executerSimulation(equipe);
                    break;

                case 3:
                    instanceSac = creerInstanceDepuisProjets(equipe);
                    break;

                case 4:
                    if (instanceSac == null) {
                        System.out.println("⚠ Vous devez d'abord créer une instance SacADos (option 3).");
                    } else {
                        testerSolveurs(instanceSac);
                    }
                    break;

                case 5:
                    if (instanceSac == null) {
                        System.out.println("⚠ Vous devez d'abord créer une instance SacADos (option 3).");
                    } else {
                        testerHillClimbing(instanceSac);
                    }
                    break;

                case 0:
                    System.out.println("👋 Au revoir !");
                    return;

                default:
                    System.out.println("⚠ Choix invalide !");
            }
        }
    }

    // ============================
    //       MENU PRINCIPAL
    // ============================
    private static void afficherMenuPrincipal() {
        System.out.println("\n===============================");
        System.out.println("    MENU PRINCIPAL DU PROJET");
        System.out.println("===============================");
        System.out.println("1. Afficher l'équipe municipale");
        System.out.println("2. Exécuter un cycle de simulation");
        System.out.println("3. Générer une instance SacADos depuis les projets");
        System.out.println("4. Tester les solveurs gloutons");
        System.out.println("5. Tester le Hill Climbing");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static int lireEntier(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrée invalide. Veuillez entrer un nombre entier.");
            }
        }
    }


    // ============================
    //    1) Construction équipe
    // ============================
    private static EquipeMunicipale construireEquipeDemonstration() {

        Elu elu = new Elu("Martin", "Pierre", 45);

        List<Evaluateur> evaluateurs = List.of(
                new Evaluateur("Dupont", "Marie", 35, TypeCout.ECONOMIQUE),
                new Evaluateur("Durant", "Sophie", 40, TypeCout.SOCIAL),
                new Evaluateur("Bernard", "Luc", 38, TypeCout.ENVIRONNEMENTAL)
        );

        List<Expert> experts = List.of(
                new Expert("Leroy", "Jean", 42, EnumSet.of(Secteur.SPORT, Secteur.EDUCATION)),
                new Expert("Moreau", "Claire", 39, EnumSet.of(Secteur.SANTE, Secteur.CULTURE)),
                new Expert("Simon", "Paul", 44, EnumSet.of(Secteur.ATTRACTIVITE_ECONOMIQUE))
        );

        return new EquipeMunicipale(elu, evaluateurs, experts);
    }


    // ============================
    //     2) Simulation équipe
    // ============================
    private static void executerSimulation(EquipeMunicipale equipe) {
        int nb = lireEntier("Combien de projets par expert ? ");
        equipe.executerCycleSimulation(nb);
        equipe.afficherProjets();
    }



    // ============================
    //     3) Vers SacADos
    // ============================
    private static SacADos creerInstanceDepuisProjets(EquipeMunicipale equipe) {

        if (equipe.getProjetsEtudies().isEmpty()) {
            System.out.println("⚠ Aucun projet évalué. Lancez d'abord une simulation.");
            return null;
        }

        System.out.println("Choisir le mode de génération de SacADos :");
        System.out.println("1. Budgets = coûts (eco, social, env)");
        System.out.println("2. Budgets = secteurs (5 secteurs)");
        System.out.print("Votre choix : ");

        int choix = lireEntier("Votre choix : ");


        if (choix == 1) {
            int[] budgets = {80000, 80000, 80000};
            SacADos sac = VersSacADos.depuisProjetSelonCouts(equipe.getProjetsEtudies(), budgets);
            System.out.println("➤ Instance SacADos créée (3 dimensions).");
            return sac;
        }

        else if (choix == 2) {
            int[] budgetsSecteurs = {100000, 100000, 100000, 100000, 100000};
            SacADos sac = VersSacADos.depuisProjetSelonSecteurs(equipe.getProjetsEtudies(), budgetsSecteurs);
            System.out.println("➤ Instance SacADos créée (5 secteurs).");
            return sac;
        }

        else {
            System.out.println("⚠ Choix invalide.");
            return null;
        }
    }


    // ============================
    //     4) Solveurs gloutons
    // ============================
    private static void testerSolveurs(SacADos sac) {

        System.out.println("\n=== Solveur Glouton AJOUT ===");
        GloutonAjoutSolver gAjout = new GloutonAjoutSolver();
        List<Objet> s1 = gAjout.resoudre(sac, Comparateurs.f_somme());
        afficherSolution("Glouton Ajout", s1, sac);

        System.out.println("\n=== Solveur Glouton RETRAIT ===");
        GloutonRetraitSolver gRetrait = new GloutonRetraitSolver();
        List<Objet> s2 = gRetrait.resoudre(sac, Comparateurs.f_somme(), Comparateurs.f_max());
        afficherSolution("Glouton Retrait", s2, sac);
    }


    // ============================
    //        5) Hill Climbing
    // ============================
    private static void testerHillClimbing(SacADos sac) {

        System.out.println("\n=== Hill Climbing ===");
        HillClimbingSolver hc = new HillClimbingSolver();

        List<Objet> solutionInitiale = new GloutonAjoutSolver()
                .resoudre(sac, Comparateurs.f_somme());

        List<Objet> solHC = hc.resoudre(sac, solutionInitiale);

        afficherSolution("Hill Climbing", solHC, sac);
    }


    // ============================
    //     (Utilitaire) Affichage
    // ============================
    private static void afficherSolution(String titre, List<Objet> sol, SacADos sac) {
        System.out.println("\n--- " + titre + " ---");
        for (Objet o : sol) {
            System.out.println("  * " + o);
        }
        System.out.println("Utilité totale = " + sac.utiliteTotale(sol));
    }
}
