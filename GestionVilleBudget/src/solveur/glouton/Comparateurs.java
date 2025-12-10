package solveur.glouton;

import java.util.Comparator;
import java.util.List;

import sacADos.Objet;

/**
 * Fournit différents comparateurs permettant de trier les objets
 * selon des critères gloutons pour la sélection dans un sac à dos
 * multidimensionnel.
 *
 * Chaque méthode retourne un {@link Comparator} permettant d’ordonner
 * une liste d’objets selon un critère d’efficacité :
 *   utilité / somme des coûts
 *   utilité / coût maximal
 *   utilité / coût dans la dimension la plus en dépassement
 */
public class Comparateurs {

    /**
     * Critère glouton : f(oi) = utilité / somme des coûts.
     * Tri décroissant : les objets les plus « rentables » en premier.
     *
     * @return comparateur basé sur l’efficacité globale
     */
    public static Comparator<Objet> f_somme() {
        return (o1, o2) -> {
            double r1 = (double) o1.getUtilite() / somme(o1.getCouts());
            double r2 = (double) o2.getUtilite() / somme(o2.getCouts());
            return Double.compare(r2, r1);
        };
    }

    /**
     * Critère glouton : fmax(oi) = utilité / coût maximal.
     * Favorise les objets dont la contrainte la plus restrictive est « peu coûteuse ».
     *
     * @return comparateur basé sur le coût maximal
     */
    public static Comparator<Objet> f_max() {
        return (o1, o2) -> {
            double r1 = (double) o1.getUtilite() / max(o1.getCouts());
            double r2 = (double) o2.getUtilite() / max(o2.getCouts());
            return Double.compare(r2, r1);
        };
    }

    /**
     * Critère fmv : utilité / coût dans la dimension la plus en dépassement.
     *
     * Ce critère prend en compte l’état actuel d’une sélection d’objets
     * afin de privilégier des objets qui n’aggravent pas la dimension
     * déjà la plus critique.
     *
     * @param budgets    budgets disponibles dans chaque dimension
     * @param selection  objets déjà sélectionnés
     * @return comparateur adaptatif tenant compte de la situation courante
     */
    public static Comparator<Objet> fmv(int[] budgets, List<Objet> selection) {
        return (o1, o2) -> {
            double score1 = scoreFMV(o1, budgets, selection);
            double score2 = scoreFMV(o2, budgets, selection);
            return Double.compare(score2, score1); // tri décroissant
        };
    }

    /* ============================================================
       Méthodes utilitaires privées
       ============================================================ */

    /** Retourne la somme d’un tableau. */
    private static int somme(int[] t) {
        int s = 0;
        for (int v : t) s += v;
        return s;
    }

    /** Retourne le maximum d’un tableau. */
    private static int max(int[] t) {
        int m = t[0];
        for (int v : t) if (v > m) m = v;
        return m;
    }

    /**
     * Calcule le score FMV = utilité / coût dans la dimension la plus dépassée.
     *
     * @param o          objet testé
     * @param budgets    budgets maximaux
     * @param selection  liste d'objets déjà choisis
     * @return score FMV de l'objet
     */
    private static double scoreFMV(Objet o, int[] budgets, List<Objet> selection) {

        int dimension = budgets.length;
        int[] consommation = new int[dimension];

        // consommation actuelle
        for (Objet obj : selection)
            for (int i = 0; i < dimension; i++)
                consommation[i] += obj.getCouts()[i];

        // repérer la dimension la plus critique
        int worstDim = 0;
        int maxDepassement = Integer.MIN_VALUE;

        for (int i = 0; i < dimension; i++) {
            int diff = consommation[i] - budgets[i];
            if (diff > maxDepassement) {
                maxDepassement = diff;
                worstDim = i;
            }
        }

        // éviter division par zéro
        int cout = o.getCouts()[worstDim];
        if (cout == 0) return Double.POSITIVE_INFINITY;

        return (double) o.getUtilite() / cout;
    }
}
