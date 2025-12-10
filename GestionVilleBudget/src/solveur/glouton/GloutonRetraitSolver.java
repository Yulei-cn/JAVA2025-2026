package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Méthode gloutonne « à retrait ».
 *
 * Étapes :
 *   On part de S = O (sélection complète)
 *   On retire les objets du moins intéressant au plus intéressant
 *       jusqu’à retrouver l’admissibilité
 *   Une fois admissible, on tente de réajouter des objets avec
 *       une méthode gloutonne « à ajout »
 *
 * Cette méthode combine un nettoyage (retrait) puis une amélioration (ajout).
 */
public class GloutonRetraitSolver {

    /**
     * Résout le sac à dos multidimensionnel via une approche « à retrait ».
     *
     * @param instance         instance du sac à dos
     * @param compRetrait      comparateur définissant l’ordre du « moins intéressant »
     * @param compAjout        comparateur pour la phase d’ajout
     * @return sélection finale admissible
     */
    public List<Objet> resoudre(
            SacADos instance,
            Comparator<Objet> compRetrait,
            Comparator<Objet> compAjout) {

        // --- 1) S = O (copie de tous les objets)
        List<Objet> selection = new ArrayList<>(instance.getObjets());

        // --- 2) Trier du moins intéressant AU plus intéressant
        selection.sort(compRetrait); // compRetrait doit fournir cet ordre

        // --- 3) Phase de retrait : on enlève tant que ce n’est pas admissible
        for (Objet o : new ArrayList<>(selection)) {
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        // Si pas admissible même après retrait total → renvoyer vide
        if (!instance.estAdmissible(selection)) {
            return new ArrayList<>();
        }

        // --- 4) Phase d’ajout
        // Repartir de la liste filtrée → on cherche à améliorer
        List<Objet> debut = new ArrayList<>(selection);

        List<Objet> resultat = new ArrayList<>(debut);
        List<Objet> tous = instance.getObjets();

        // objets qui ne sont pas déjà dans la sélection filtrée
        List<Objet> candidats = new ArrayList<>();
        for (Objet o : tous)
            if (!debut.contains(o))
                candidats.add(o);

        // tri des candidats selon critère d’ajout
        candidats.sort(compAjout);

        // tentative d’ajout
        for (Objet obj : candidats) {
            resultat.add(obj);
            if (!instance.estAdmissible(resultat)) {
                resultat.remove(obj);
            }
        }

        return resultat;
    }
}
