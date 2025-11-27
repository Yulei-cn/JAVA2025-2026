package solveur.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémente la méthode du Hill Climbing pour le problème
 * du sac à dos multidimensionnel.
 *
 * <p>
 * Le voisinage est défini par :
 * S' = (S \ E) ∪ A
 * avec |E| ≤ t et |A| ≤ t.
 * </p>
 */
public class HillClimbingSolver {

    private Random rnd = new Random();

    /**
     * Lance une recherche locale via Hill Climbing.
     *
     * @param instance         instance du sac à dos
     * @param solutionInitiale solution admissible obtenue par un glouton
     * @param t                taille du voisinage (1 ou 2)
     * @param maxPlateauMoves  nombre de mouvements possibles sur plateau
     * @return solution atteignant un optimum local
     */
    public List<Objet> resoudre(SacADos instance,
                                List<Objet> solutionInitiale,
                                int t,
                                int maxPlateauMoves) {

        List<Objet> solution = new ArrayList<>(solutionInitiale);
        int utiliteCourante = instance.utiliteTotale(solution);

        boolean amelioration = true;

        while (amelioration) {
            amelioration = false;

            List<Objet> meilleurVoisin = solution;
            int meilleureUtilite = utiliteCourante;

            int plateauMoves = maxPlateauMoves;

            // === Génération du voisinage (E et A de taille <= t) ===
            for (Objet remove : instance.getObjets()) {

                // essayer de retirer remove (si présent)
                List<Objet> voisinBase = new ArrayList<>(solution);
                voisinBase.remove(remove);

                // essayer d'ajouter jusqu'à t objets différents
                for (Objet add : instance.getObjets()) {
                    if (solution.contains(add)) continue; // éviter réinsertion

                    List<Objet> voisin = new ArrayList<>(voisinBase);
                    voisin.add(add);

                    // admissible ?
                    if (!instance.estAdmissible(voisin))
                        continue;

                    int utilite = instance.utiliteTotale(voisin);

                    if (utilite > meilleureUtilite) {
                        meilleureUtilite = utilite;
                        meilleurVoisin = voisin;
                        amelioration = true;
                    }
                    else if (utilite == meilleureUtilite && plateauMoves > 0) {
                        plateauMoves--;
                        meilleurVoisin = voisin;
                        amelioration = true;
                    }
                }
            }

            solution = meilleurVoisin;
            utiliteCourante = meilleureUtilite;
        }

        return solution;
    }
}
