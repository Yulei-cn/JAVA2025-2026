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
        //System.out.print("Votre choix : ");
    }
    
    private static int lireChoixSousMenu(String msg) {
        while (true) {
            int x = lireEntier(msg);
            if (x >= 0) return x;   // 0 = retour
            System.out.println("⚠ Choix invalide.");
        }
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
        System.out.println("0. Retour au menu principal");

        int choix = lireChoixSousMenu("Votre choix : ");
        if (choix == 0) return null;   // ★ 返回主菜单

        if (choix == 1) {
            System.out.println("=== Définir les budgets par type de coût ===");
            int bEco = lireEntier("Budget économique : ");
            int bSoc = lireEntier("Budget social : ");
            int bEnv = lireEntier("Budget environnemental : ");

            int[] budgets = { bEco, bSoc, bEnv };
            System.out.println("➤ Instance SacADos créée (3 dimensions).");
            return VersSacADos.depuisProjetSelonCouts(equipe.getProjetsEtudies(), budgets);
        }

        if (choix == 2) {
            System.out.println("=== Définir les budgets par secteur ===");
            int bSport = lireEntier("Sport : ");
            int bSante = lireEntier("Santé : ");
            int bEdu = lireEntier("Éducation : ");
            int bCulture = lireEntier("Culture : ");
            int bEco = lireEntier("Attractivité économique : ");

            int[] budgets = { bSport, bSante, bEdu, bCulture, bEco };
            System.out.println("➤ Instance SacADos créée (5 secteurs).");
            return VersSacADos.depuisProjetSelonSecteurs(equipe.getProjetsEtudies(), budgets);
        }

        System.out.println("⚠ Choix invalide.");
        return null;
    }



    // ============================
    //     4) Solveurs gloutons
    // ============================
    private static void testerSolveurs(SacADos sac) {

        while (true) {

            System.out.println("\n=== Tester les solveurs gloutons ===");
            System.out.println("1. Glouton Ajout");
            System.out.println("2. Glouton Retrait");
            System.out.println("3. Les deux");
            System.out.println("0. Retour");
            int choix = lireChoixSousMenu("Votre choix : ");

            if (choix == 0) return;  // ★ 返回主菜单

            if (choix == 1 || choix == 3) {
                System.out.println("\n=== Solveur Glouton AJOUT ===");
                GloutonAjoutSolver g1 = new GloutonAjoutSolver();
                afficherSolution("Glouton Ajout", g1.resoudre(sac, Comparateurs.f_somme()), sac);
            }

            if (choix == 2 || choix == 3) {
                System.out.println("\n=== Solveur Glouton RETRAIT ===");
                GloutonRetraitSolver g2 = new GloutonRetraitSolver();
                afficherSolution("Glouton Retrait",
                        g2.resoudre(sac, Comparateurs.f_somme(), Comparateurs.f_max()), sac);
            }
        }
    }



    // ============================
    //        5) Hill Climbing
    // ============================
    private static void testerHillClimbing(SacADos sac) {

        while (true) {
            System.out.println("\n=== Tester le Hill Climbing ===");
            System.out.println("1. HC standard (t = 1)");
            System.out.println("2. HC avec plateau (t = 1, plateau = 3)");
            System.out.println("0. Retour");

            int choix = lireChoixSousMenu("Votre choix : ");
            if (choix == 0) return;

            List<Objet> init = new GloutonAjoutSolver().resoudre(sac, Comparateurs.f_somme());
            HillClimbingSolver hc = new HillClimbingSolver();

            if (choix == 1) {
                afficherSolution("Hill Climbing (t=1)", hc.resoudre(sac, init), sac);
            }

            if (choix == 2) {
                afficherSolution("Hill Climbing (plateau=3)",
                        hc.resoudre(sac, init, 1, 3), sac);
            }
        }
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
