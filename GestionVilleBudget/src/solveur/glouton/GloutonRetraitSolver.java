package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémente la méthode gloutonne « à retrait ».
 *
 * <p>
 * Principe général :
 * <ul>
 *   <li>On part de la sélection complète S = O</li>
 *   <li>On retire les objets du moins intéressant au plus intéressant
 *       jusqu'à ce que la solution devienne admissible</li>
 *   <li>Une fois admissible, on tente d'améliorer la solution via une phase
 *       d’ajout utilisant un second comparateur</li>
 * </ul>
 *
 * <p>Cette méthode combine un nettoyage (retrait) puis une optimisation locale (ajout).
 *
 * @author ZHU YULEI
 * @version 2.0
 */
public class GloutonRetraitSolver {

    /**
     * Applique l’algorithme glouton « à retrait » sur une instance du sac à dos.
     *
     * @param instance    instance du sac à dos multidimensionnel
     * @param compRetrait comparateur définissant l’ordre « du moins intéressant au plus intéressant »
     * @param compAjout   comparateur utilisé lors de la phase d’ajout pour améliorer la solution
     * @return une solution admissible obtenue par retrait puis amélioration gloutonne
     */
    public List<Objet> resoudre(
            SacADos instance,
            Comparator<Objet> compRetrait,
            Comparator<Objet> compAjout) {

        // --- 1) S = O (copie complète des objets)
        List<Objet> selection = new ArrayList<>(instance.getObjets());

        // --- 2) Trier du moins intéressant au plus intéressant
        selection.sort(compRetrait);

        // --- 3) Phase de retrait : on enlève tant que la solution reste inadmissible
        for (Objet o : new ArrayList<>(selection)) {
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        // Si ce n'est toujours pas admissible → renvoyer vide
        if (!instance.estAdmissible(selection)) {
            return new ArrayList<>();
        }

        // --- 4) Phase d’ajout : tentative d'amélioration
        List<Objet> resultat = new ArrayList<>(selection);
        List<Objet> candidats = new ArrayList<>();

        // candidats = objets non présents dans la sélection actuelle
        for (Objet o : instance.getObjets()) {
            if (!selection.contains(o)) {
                candidats.add(o);
            }
        }

        // tri selon critère d’ajout
        candidats.sort(compAjout);

        // tentative d’ajout gloutonne
        for (Objet o : candidats) {
            resultat.add(o);
            if (!instance.estAdmissible(resultat)) {
                resultat.remove(o);
            }
        }

        return resultat;
    }
}
