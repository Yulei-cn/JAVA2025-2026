package solveur.hillclimbing;

import java.util.ArrayList;
import java.util.List;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémentation de la méthode Hill Climbing pour le sac-à-dos multidimensionnel.
 */
public class HillClimbingSolver {

    /**
     * Améliore une solution initiale via Hill Climbing
     *
     * @param instance          instance du sac-à-dos multidimensionnel
     * @param solutionInitiale  solution obtenue par une méthode gloutonne
     * @return la meilleure solution trouvée (optimum local)
     */
    public List<Objet> resoudre(SacADos instance, List<Objet> solutionInitiale) {

        List<Objet> solutionCourante = new ArrayList<>(solutionInitiale);
        boolean amelioration = true;

        while (amelioration) {
            amelioration = false;
            List<Objet> meilleurVoisin = solutionCourante;
            int meilleureUtilite = instance.utiliteTotale(solutionCourante);

            // explorer le voisinage : retirer un objet + essayer ajouter un autre
            for (Objet remove : new ArrayList<>(solutionCourante)) {

                List<Objet> voisin = new ArrayList<>(solutionCourante);
                voisin.remove(remove);

                for (Objet add : instance.getObjets()) {
                    if (!voisin.contains(add)) {
                        voisin.add(add);

                        if (instance.estAdmissible(voisin)) {
                            int utiliteVoisin = instance.utiliteTotale(voisin);

                            if (utiliteVoisin > meilleureUtilite) {
                                meilleurVoisin = new ArrayList<>(voisin);
                                meilleureUtilite = utiliteVoisin;
                                amelioration = true;
                            }
                        }

                        voisin.remove(add); // revenir à voisin avant ajout
                    }
                }
            }

            solutionCourante = meilleurVoisin;
        }

        return solutionCourante;
    }
}
