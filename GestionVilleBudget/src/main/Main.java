package main;

import java.util.*;
import equipe.*;
import sacADos.*;
import solveur.glouton.*;
import solveur.hillclimbing.*;

/**
 * Classe principale du projet. 
 * 
 * <p>
 * Cette classe contient un menu interactif permettant :
 * <ul>
 *   <li>d’afficher l’équipe municipale</li>
 *   <li>d’exécuter un cycle de simulation des projets</li>
 *   <li>de générer une instance du problème du sac à dos</li>
 *   <li>de tester les méthodes gloutonnes</li>
 *   <li>de tester la méthode du Hill Climbing</li>
 * </ul>
 * </p>
 *
 * @author ZHU YULEI
 * @version 5.0
 */
public class Main {

    /** Scanner utilisé pour toutes les entrées utilisateur. */
    private static Scanner scanner = new Scanner(System.in);


    /**
     * Point d’entrée du programme.
     *
     * @param args arguments de lancement (non utilisés)
     */
    public static void main(String[] args) {

        EquipeMunicipale equipe = construireEquipeDemonstration();
        SacADos instanceSac = null;

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


    // ============================================================
    //                     MENU PRINCIPAL
    // ============================================================

    /**
     * Affiche le menu principal.
     */
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
    }


    /**
     * Lecture sécurisée d’un entier dans un sous-menu.
     *
     * @param msg texte affiché avant la saisie
     * @return un entier >= 0, où 0 signifie « retour »
     */
    private static int lireChoixSousMenu(String msg) {
        while (true) {
            int x = lireEntier(msg);
            if (x >= 0) return x;
            System.out.println("⚠ Choix invalide.");
        }
    }


    /**
     * Lecture sécurisée d’un entier.
     *
     * @param msg texte affiché avant la saisie
     * @return un entier saisi par l’utilisateur
     */
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


    // ============================================================
    //               CONSTRUCTION DE L’EQUIPE
    // ============================================================

    /**
     * Construit une équipe municipale standard pour les tests.
     *
     * @return une instance d’EquipeMunicipale
     */
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


    // ============================================================
    //                  SIMULATION DES PROJETS
    // ============================================================

    /**
     * Lance un cycle de simulation pour tous les experts.
     *
     * @param equipe équipe municipale active
     */
    private static void executerSimulation(EquipeMunicipale equipe) {
        int nb = lireEntier("Combien de projets par expert ? ");
        equipe.executerCycleSimulation(nb);
        equipe.afficherProjets();
    }


    // ============================================================
    //        CONSTRUCTION D’UNE INSTANCE SAC À DOS
    // ============================================================

    /**
     * Crée une instance SacADos à partir des projets évalués.
     *
     * @param equipe équipe municipale contenant les projets
     * @return instance de SacADos ou null si retour
     */
    private static SacADos creerInstanceDepuisProjets(EquipeMunicipale equipe) {

        if (equipe.getProjetsEtudies().isEmpty()) {
            System.out.println("⚠ Aucun projet évalué. Lancez d'abord une simulation.");
            return null;
        }

        System.out.println("Choisir le mode de génération de SacADos :");
        System.out.println("1. Budgets = coûts (éco / social / env)");
        System.out.println("2. Budgets = secteurs (5 secteurs)");
        System.out.println("0. Retour au menu principal");

        int choix = lireChoixSousMenu("Votre choix : ");
        if (choix == 0) return null;

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


    // ============================================================
    //               TEST DES SOLVEURS GLOUTONS
    // ============================================================

    /**
     * Menu interactif permettant de tester les méthodes gloutonnes.
     *
     * @param sac instance du problème du sac à dos
     */
    private static void testerSolveurs(SacADos sac) {

        while (true) {

            System.out.println("\n=== Tester les solveurs gloutons ===");
            System.out.println("1. Glouton Ajout");
            System.out.println("2. Glouton Retrait");
            System.out.println("3. Les deux");
            System.out.println("0. Retour");

            int choix = lireChoixSousMenu("Votre choix : ");
            if (choix == 0) return;

            if (choix == 1 || choix == 3) {
                GloutonAjoutSolver g1 = new GloutonAjoutSolver();
                afficherSolution("Glouton Ajout", g1.resoudre(sac, Comparateurs.f_somme()), sac);
            }

            if (choix == 2 || choix == 3) {
                GloutonRetraitSolver g2 = new GloutonRetraitSolver();
                afficherSolution("Glouton Retrait",
                        g2.resoudre(sac, Comparateurs.f_somme(), Comparateurs.f_max()),
                        sac);
            }
        }
    }


    // ============================================================
    //               TEST DU HILL CLIMBING
    // ============================================================

    /**
     * Menu interactif permettant de tester la méthode du Hill Climbing.
     *
     * @param sac instance du problème du sac à dos
     */
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
                afficherSolution("Hill Climbing (t = 1)", hc.resoudre(sac, init), sac);
            }

            if (choix == 2) {
                afficherSolution("Hill Climbing (plateau = 3)",
                        hc.resoudre(sac, init, 1, 3), sac);
            }
        }
    }


    // ============================================================
    //                    AFFICHAGE DES SOLUTIONS
    // ============================================================

    /**
     * Affiche une solution trouvée par un solveur.
     *
     * @param titre nom du solveur
     * @param sol liste d’objets sélectionnés
     * @param sac instance du problème pour calculer l’utilité totale
     */
    private static void afficherSolution(String titre, List<Objet> sol, SacADos sac) {
        System.out.println("\n--- " + titre + " ---");
        for (Objet o : sol) {
            System.out.println("  * " + o);
        }
        System.out.println("Utilité totale = " + sac.utiliteTotale(sol));
    }
}
