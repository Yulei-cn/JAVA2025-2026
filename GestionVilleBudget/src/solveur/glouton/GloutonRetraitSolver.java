package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Solver glouton "à retrait" :
 *  - On part de S = O (tous les objets)
 *  - On retire les moins intéressants jusqu'à respecter le budget
 *  - Puis on tente de rajouter des objets avec un glouton à ajout
 */
public class GloutonRetraitSolver {

    /**
     * Résout le sac-à-dos multidimensionnel via une méthode gloutonne "à retrait".
     *
     * @param instance instance du sac à dos
     * @param comparateur comparateur des objets pour définir "moins intéressant" → "plus intéressant"
     * @param comparateurAjout comparateur à utiliser pour la phase d'ajout (glouton ajout)
     * @return sélection finale d'objets admissible
     */
    public List<Objet> resoudre(SacADos instance, Comparator<Objet> comparateur,
                                Comparator<Objet> comparateurAjout) {

        // S = O (copie de la liste initiale)
        List<Objet> selection = new ArrayList<>(instance.getObjets());

        // tri du moins intéressant → plus intéressant
        selection.sort(comparateur);

        // phase de retrait
        for (Objet o : new ArrayList<>(selection)) {
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        // si après retrait on est OK → on tente un ajout glouton
        GloutonAjoutSolver gloutonAjout = new GloutonAjoutSolver();
        return gloutonAjout.resoudre(instance, comparateurAjout);
    }
}
