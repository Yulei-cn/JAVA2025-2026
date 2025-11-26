package sacADos;

import java.util.ArrayList;
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
}
