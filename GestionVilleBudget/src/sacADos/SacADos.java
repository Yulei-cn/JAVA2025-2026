package sacADos;

import java.util.List;

/**
 * Représente un sac à dos multidimensionnel utilisé pour la sélection optimale d’objets.
 *
 * Le sac à dos est défini par :
 *   une dimension k (nombre de contraintes ou budgets)
 *   un tableau de budgets, un par dimension
 *   une liste d’objets pouvant être sélectionnés
 *
 * Il fournit des méthodes permettant :
 *   de vérifier si une sélection respecte les contraintes
 *   de calculer l’utilité totale d’une sélection
 */
public class SacADos {

    /** Nombre de dimensions (ou contraintes) */
    private int dimension;

    /** Budgets maximaux pour chaque dimension */
    private int[] budgets;

    /** Liste d’objets disponibles */
    private List<Objet> objets;

    /**
     * Construit un sac à dos multidimensionnel.
     *
     * @param dimension nombre de contraintes
     * @param budgets   budgets associés à chaque dimension
     * @param objets    liste des objets disponibles
     */
    public SacADos(int dimension, int[] budgets, List<Objet> objets) {

        if (dimension <= 0) {
            throw new IllegalArgumentException("La dimension doit être positive.");
        }

        if (budgets == null || budgets.length != dimension) {
            throw new IllegalArgumentException(
                "Le tableau de budgets doit avoir une taille égale à la dimension (" + dimension + ")."
            );
        }

        if (objets == null) {
            throw new NullPointerException("La liste des objets ne peut pas être null.");
        }

        this.dimension = dimension;
        this.budgets = budgets;
        this.objets = objets;
    }


    public int getDimension() {
        return dimension;
    }

    public int[] getBudgets() {
        return budgets;
    }

    public List<Objet> getObjets() {
        return objets;
    }

    /**
     * Vérifie si une sélection d’objets respecte les contraintes du sac à dos.
     *
     * @param selection liste des objets choisis
     * @return true si la sélection est admissible, false sinon
     */
    public boolean estAdmissible(List<Objet> selection) {
        int[] consommation = new int[dimension];

        for (Objet o : selection) {
            for (int i = 0; i < dimension; i++) {
                consommation[i] += o.getCouts()[i];
                if (consommation[i] > budgets[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calcule l’utilité totale d’une sélection d’objets.
     *
     * @param selection liste des objets choisis
     * @return utilité totale
     */
    public int utiliteTotale(List<Objet> selection) {
        return selection.stream()
                .mapToInt(Objet::getUtilite)
                .sum();
    }
}
