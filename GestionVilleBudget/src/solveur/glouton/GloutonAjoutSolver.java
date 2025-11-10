package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Solver glouton "à ajout" :
 *  - trie les objets selon un critère donné (Comparator)
 *  - ajoute les objets un par un tant que les budgets ne sont pas dépassés
 */
public class GloutonAjoutSolver {

    /**
     * Résout le problème du sac à dos multidimensionnel via une méthode gloutonne à **ajout**
     *
     * @param instance    l’instance du sac à dos (contient objets + budgets)
     * @param comparateur permet de définir le critère "plus intéressant"
     * @return une sélection d'objets admissible (respecte les budgets)
     */
    public List<Objet> resoudre(SacADos instance, Comparator<Objet> comparateur) {

        // copier la liste d'objets pour ne pas modifier l’instance
        List<Objet> objetsTries = new ArrayList<>(instance.getObjets());

        // tri selon le critère
        objetsTries.sort(comparateur);

        List<Objet> selection = new ArrayList<>();

        // essai d'ajout dans l'ordre
        for (Objet o : objetsTries) {
            selection.add(o);

            // si dépasse budget → on retire l'objet
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        return selection;
    }
}
