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

	    if (projets == null || projets.isEmpty()) {
	        throw new IllegalArgumentException("La liste des projets ne peut pas être vide.");
	    }
	    if (budgets == null || budgets.length != 3) {
	        throw new IllegalArgumentException("Les budgets doivent contenir 3 valeurs.");
	    }

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

	    if (projets == null || projets.isEmpty()) {
	        throw new IllegalArgumentException("La liste des projets ne peut pas être vide.");
	    }
	    if (budgetsSecteurs == null || budgetsSecteurs.length != 5) {
	        throw new IllegalArgumentException("Les budgets par secteur doivent contenir 5 valeurs.");
	    }

	    List<Objet> objets = new ArrayList<>();

	    for (Projet p : projets) {

	        int[] couts = new int[5];
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
	    try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
	
	        String[] header = br.readLine().trim().split("\\s+");
	        int n = Integer.parseInt(header[0]);
	        int k = Integer.parseInt(header[1]);
	
	        int[] utilites = new int[n];
	        for (int i = 0; i < n; i++) {
	            utilites[i] = Integer.parseInt(br.readLine().trim());
	        }
	
	        int[][] couts = new int[k][n];
	        for (int dim = 0; dim < k; dim++) {
	            String[] ligne = br.readLine().trim().split("\\s+");
	            for (int obj = 0; obj < n; obj++) {
	                couts[dim][obj] = Integer.parseInt(ligne[obj]);
	            }
	        }
	
	        String[] ligneBudgets = br.readLine().trim().split("\\s+");
	        int[] budgets = new int[k];
	        for (int dim = 0; dim < k; dim++) {
	            budgets[dim] = Integer.parseInt(ligneBudgets[dim]);
	        }
	
	        List<Objet> objets = new ArrayList<>();
	        for (int i = 0; i < n; i++) {
	            int[] coutObjet = new int[k];
	            for (int dim = 0; dim < k; dim++)
	                coutObjet[dim] = couts[dim][i];
	            objets.add(new Objet(utilites[i], coutObjet));
	        }
	
	        return new SacADos(k, budgets, objets);
	    }
	    catch (FileNotFoundException e) {
	        throw new IllegalArgumentException("❌ Fichier introuvable : " + chemin, e);
	    }
	    catch (IOException | NumberFormatException e) {
	        throw new RuntimeException("❌ Erreur lors de la lecture du fichier : " + chemin, e);
	    }
	}


}
