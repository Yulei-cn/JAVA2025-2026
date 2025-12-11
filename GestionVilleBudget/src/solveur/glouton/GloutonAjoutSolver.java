package solveur.glouton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémente l'algorithme glouton « à ajout ».
 *
 * <p>
 * Le principe est le suivant :
 * <ul>
 *   <li>les objets sont triés selon un critère donné (via un {@link Comparator}) ;</li>
 *   <li>on parcourt ensuite les objets dans cet ordre ;</li>
 *   <li>chaque objet est ajouté si et seulement si les contraintes du sac à dos restent respectées.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette approche permet d'obtenir rapidement une solution admissible,
 * mais elle n'est pas garantie optimale.
 * </p>
 *
 * @author ZHU YULEI
 * @version 2.0
 */
public class GloutonAjoutSolver {

    /**
     * Applique la méthode gloutonne « à ajout » pour sélectionner une liste d’objets.
     *
     * @param instance    instance du sac à dos contenant les objets et les budgets
     * @param comparateur comparateur définissant l’ordre de priorité des objets
     * @return une liste d’objets représentant la solution gloutonne admissible
     */
    public List<Objet> resoudre(SacADos instance, Comparator<Objet> comparateur) {

        // copie des objets pour éviter de modifier la liste originale
        List<Objet> objetsTries = new ArrayList<>(instance.getObjets());
        objetsTries.sort(comparateur);

        List<Objet> selection = new ArrayList<>();

        // sélection gloutonne
        for (Objet o : objetsTries) {
            selection.add(o);

            // si l'ajout viole un budget → on retire l'objet
            if (!instance.estAdmissible(selection)) {
                selection.remove(o);
            }
        }

        return selection;
    }
}
