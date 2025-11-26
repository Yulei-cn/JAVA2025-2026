package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémente une méthode gloutonne « à ajout ».
 *
 * <p>
 * Les objets sont triés selon un critère (fournit via un {@link Comparator}),
 * puis considérés un par un :
 * <ul>
 *   <li>si l’objet peut être ajouté sans dépasser les budgets → il est gardé</li>
 *   <li>sinon → il est ignoré</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette approche fournit une solution admissible rapide, mais pas nécessairement optimale.
 * </p>
 */
public class GloutonAjoutSolver {

    /**
     * Exécute la méthode gloutonne « à ajout ».
     *
     * @param instance    instance du sac à dos (objets + budgets)
     * @param comparateur critère définissant l’ordre « du plus intéressant au moins intéressant »
     * @return liste d’objets admissibles constituant la solution gloutonne
     */
    public List<Objet> resoudre(SacADos instance, Comparator<Objet> comparateur) {

        // Copie des objets pour éviter de modifier les données de l’instance
        List<Objet> objetsTries = new ArrayList<>(instance.getObjets());
        objetsTries.sort(comparateur);

        List<Objet> selection = new ArrayList<>();

        // Parcours glouton
        for (Objet o : objetsTries) {
            selection.add(o);

            // Si l’ajout rend la solution non admissible → on annule
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        return selection;
    }
}
