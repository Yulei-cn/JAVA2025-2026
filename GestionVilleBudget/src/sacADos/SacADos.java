package sacADos;

import java.util.List;

/**
 * Représente un sac à dos multidimensionnel.
 * Il contient :
 *  - une dimension k (nb de budgets)
 *  - un tableau de budgets
 *  - une liste d’objets disponibles
 */
public class SacADos {

    private int dimension;
    private int[] budgets;
    private List<Objet> objets;

    public SacADos(int dimension, int[] budgets, List<Objet> objets) {
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

    /** Vérifie si une sélection respecte les budgets */
    public boolean estAdmissible(List<Objet> selection) {
        int[] consommation = new int[dimension];

        for (Objet o : selection) {
            for (int i = 0; i < dimension; i++) {
                consommation[i] += o.getCouts()[i];
                if (consommation[i] > budgets[i]) return false;
            }
        }
        return true;
    }

    /** Retourne l’utilité totale d’une sélection */
    public int utiliteTotale(List<Objet> selection) {
        return selection.stream().mapToInt(Objet::getUtilite).sum();
    }
}
