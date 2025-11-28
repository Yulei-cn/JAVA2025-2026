package sacADos;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

import equipe.Projet;
import equipe.Secteur;
import java.util.List;

import equipe.Projet;

/**
 * Classe utilitaire permettant de convertir une liste de projets
 * en une instance de {@link SacADos}, selon différents critères.
 *
 * <p>
 * Deux modes de conversion sont fournis :
 * <ul>
 *   <li>Conversion selon les trois types de coûts (économique, social, environnemental)</li>
 *   <li>Conversion selon les secteurs d’activité</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette classe ne modifie pas les projets : elle produit simplement une
 * représentation compatible avec les solveurs du sac à dos multidimensionnel.
 * </p>
 */
public class VersSacADos {

    /**
     * Convertit une liste de projets en sac à dos multidimensionnel
     * où chaque dimension correspond à un type de coût :
     *
     * <ul>
     *   <li>coût économique</li>
     *   <li>coût social</li>
     *   <li>coût environnemental</li>
     * </ul>
     *
     * <p>
     * Pour chaque projet :
     * <ul>
     *   <li>l'utilité de l'objet est le bénéfice du projet</li>
     *   <li>les coûts sont placés dans un tableau à 3 cases</li>
     * </ul>
     * </p>
     *
     * @param projets  liste des projets évalués
     * @param budgets  budgets disponibles pour chaque type de coût (taille 3)
     * @return instance de {@link SacADos} de dimension 3
     */
    public static SacADos depuisProjetSelonCouts(List<Projet> projets, int[] budgets) {

        List<Objet> objets = new ArrayList<>();

        for (Projet p : projets) {
            int[] couts = new int[]{
                p.getCoutEconomique(),
                p.getCoutSocial(),
                p.getCoutEnvironnemental()
            };
            objets.add(new Objet(p.getBenefice(), couts));
        }

        return new SacADos(3, budgets, objets);
    }

    /**
     * Convertit une liste de projets en sac à dos multidimensionnel,
     * où chaque dimension correspond à un secteur d’activité.
     *
     * <p>
     * Dimension = nombre de secteurs connus (fixé à 5 dans ce projet).
     * </p>
     *
     * <p>
     * Chaque projet ne consomme du budget que dans SON propre secteur.
     * On n'utilise ici que le coût économique du projet.
     * </p>
     *
     * @param projets          liste des projets
     * @param budgetsSecteurs  budgets associés à chaque secteur (taille 5)
     * @return instance de {@link SacADos} de dimension 5
     */
    public static SacADos depuisProjetSelonSecteurs(List<Projet> projets, int[] budgetsSecteurs) {

        List<Objet> objets = new ArrayList<>();

        for (Projet p : projets) {

            int[] couts = new int[5];  // une case par secteur

            // le coût est mis dans la case correspondant au secteur du projet
            couts[p.getSecteur().ordinal()] = p.getCoutEconomique();

            objets.add(new Objet(p.getBenefice(), couts));
        }

        return new SacADos(5, budgetsSecteurs, objets);
    }
    
    /**
     * Lit un fichier benchmark (format Drake 2015) et construit une instance
     * de SacADos multidimensionnel à partir de celui-ci.
     *
     * Format attendu :
     * n  k  valeur_opt
     * u1
     * u2
     * ...
     * un
     * c11 c12 ... c1n
     * c21 c22 ... c2n
     * ...
     * ck1 ck2 ... ckn
     * B1 B2 ... Bk
     *
     * @param chemin  chemin vers le fichier benchmark
     * @return instance de SacADos (ou null en cas d’erreur)
     */
    public static SacADos depuisFichier(String chemin) {

        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {

            // ---- Ligne 1 : n objets, k budgets ----
            String[] header = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(header[0]);
            int k = Integer.parseInt(header[1]);

            // ---- lire les utilités ----
            int[] utilites = new int[n];
            for (int i = 0; i < n; i++) {
                utilites[i] = Integer.parseInt(reader.readLine().trim());
            }

            // ---- lire matrice des coûts (k lignes × n colonnes) ----
            int[][] couts = new int[k][n];
            for (int dim = 0; dim < k; dim++) {
                String[] ligne = reader.readLine().trim().split("\\s+");
                for (int obj = 0; obj < n; obj++) {
                    couts[dim][obj] = Integer.parseInt(ligne[obj]);
                }
            }

            // ---- lire budgets ----
            String[] ligneBudgets = reader.readLine().trim().split("\\s+");
            int[] budgets = new int[k];
            for (int dim = 0; dim < k; dim++) {
                budgets[dim] = Integer.parseInt(ligneBudgets[dim]);
            }

            // ---- créer la liste d’objets ----
            List<Objet> objets = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int[] coutObjet = new int[k];
                for (int dim = 0; dim < k; dim++)
                    coutObjet[dim] = couts[dim][i];

                objets.add(new Objet(utilites[i], coutObjet));
            }

            return new SacADos(k, budgets, objets);
        }
        catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + chemin);
            return null;
        }
    }

}
