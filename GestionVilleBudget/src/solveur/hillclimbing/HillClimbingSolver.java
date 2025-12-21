package solveur.hillclimbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sacADos.Objet;
import sacADos.SacADos;

/**
 * Implémente l'algorithme de Hill Climbing pour le problème
 * du sac à dos multidimensionnel.
 *
 * <p>
 * Le voisinage utilisé est défini par :
 * <br>
 * <code>S' = (S \ E) ∪ A</code> où |E| ≤ t et |A| ≤ t.
 * </p>
 *
 * <p>
 * L’algorithme cherche de meilleurs voisins jusqu'à atteindre un optimum local.
 * Une variante avec mouvements sur plateau est également supportée.
 * </p>
 *
 * @author ZHU YULEI
 * @version 2.0
 */
public class HillClimbingSolver {

    private Random rnd = new Random();

    /**
     * Version simplifiée avec voisinage t=1 et aucun mouvement de plateau.
     *
     * @param instance         instance du sac à dos
     * @param solutionInitiale solution initiale admissible
     * @return solution améliorée ou optimum local
     */
    public List<Objet> resoudre(SacADos instance, List<Objet> solutionInitiale) {
        return resoudre(instance, solutionInitiale, 1, 0);
    }

    /**
     * Exécute la recherche locale Hill Climbing.
     *
     * <p>
     * Le voisinage étudié se construit en retirant jusqu’à t objets et
     * en ajoutant jusqu’à t objets. L’algorithme explore tous les voisins
     * admissibles et conserve le meilleur.
     * </p>
     *
     * @param instance         instance du sac à dos à optimiser
     * @param solutionInitiale solution admissible (souvent obtenue via un glouton)
     * @param t                taille du voisinage (nombre max. d’ajouts/retraits)
     * @param maxPlateauMoves  nombre de déplacements autorisés sur plateau (utilité égale)
     * @return la meilleure solution trouvée (optimum local)
     *
     * @throws NullPointerException     si la solution initiale est null
     * @throws IllegalArgumentException si t ≤ 0 ou maxPlateauMoves < 0
     */
    public List<Objet> resoudre(
            SacADos instance,
            List<Objet> solutionInitiale,
            int t,
            int maxPlateauMoves) {

        if (solutionInitiale == null) {
            throw new NullPointerException("La solution initiale ne peut pas être null.");
        }
        if (t <= 0) {
            throw new IllegalArgumentException("Le paramètre t doit être strictement positif.");
        }
        if (maxPlateauMoves < 0) {
            throw new IllegalArgumentException("Le nombre de mouvements sur plateau doit être >= 0.");
        }

        List<Objet> solution = new ArrayList<>(solutionInitiale);
        int utiliteCourante = instance.utiliteTotale(solution);

        boolean amelioration = true;

        // =======================
        //     Boucle principale
        // =======================
        while (amelioration) {

            amelioration = false;
            List<Objet> meilleurVoisin = solution;
            int meilleureUtilite = utiliteCourante;

            int plateauMoves = maxPlateauMoves;

            // =======================
            //   Génération voisins
            // =======================
            for (Objet remove : instance.getObjets()) {

                List<Objet> voisinBase = new ArrayList<>(solution);
                voisinBase.remove(remove);

                for (Objet add : instance.getObjets()) {
                    if (solution.contains(add)) continue;

                    List<Objet> voisin = new ArrayList<>(voisinBase);
                    voisin.add(add);

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
    
    /**
     * Variante de Hill Climbing avec génération aléatoire des voisins.
     *
     * <p>
     * À chaque itération, un nombre fixé de voisins est généré aléatoirement.
     * Chaque voisin est construit en retirant jusqu'à {@code t} objets de la
     * solution courante et en ajoutant jusqu'à {@code t} objets absents.
     * </p>
     *
     * <p>
     * Contrairement à la version standard, tous les voisins possibles ne sont
     * pas explorés : seul un sous-ensemble aléatoire est considéré.
     * </p>
     *
     * @param instance          instance du sac à dos à optimiser
     * @param solutionInitiale  solution admissible initiale
     * @param t                 taille maximale des mouvements (ajouts / retraits)
     * @param nombreVoisins     nombre de voisins aléatoires générés par itération
     * @param maxPlateauMoves   nombre de mouvements autorisés sur plateau
     * @return solution correspondant à un optimum local
     *
     * @throws NullPointerException     si la solution initiale est null
     * @throws IllegalArgumentException si t ≤ 0 ou nombreVoisins ≤ 0 ou maxPlateauMoves < 0
     */
    public List<Objet> resoudreAleatoire(
            SacADos instance,
            List<Objet> solutionInitiale,
            int t,
            int nombreVoisins,
            int maxPlateauMoves) {

        if (solutionInitiale == null) {
            throw new NullPointerException("La solution initiale ne peut pas être null.");
        }
        if (t <= 0) {
            throw new IllegalArgumentException("Le paramètre t doit être strictement positif.");
        }
        if (nombreVoisins <= 0) {
            throw new IllegalArgumentException("Le nombre de voisins doit être strictement positif.");
        }
        if (maxPlateauMoves < 0) {
            throw new IllegalArgumentException("Le nombre de mouvements sur plateau doit être >= 0.");
        }

        List<Objet> solution = new ArrayList<>(solutionInitiale);
        int utiliteCourante = instance.utiliteTotale(solution);

        boolean amelioration = true;

        while (amelioration) {

            amelioration = false;
            List<Objet> meilleurVoisin = solution;
            int meilleureUtilite = utiliteCourante;
            int plateauMoves = maxPlateauMoves;

            // ===============================
            // Génération aléatoire des voisins
            // ===============================
            for (int i = 0; i < nombreVoisins; i++) {

                List<Objet> voisin = new ArrayList<>(solution);

                // --- Retrait aléatoire ---
                int nbRetraits = rnd.nextInt(t) + 1;
                for (int r = 0; r < nbRetraits && !voisin.isEmpty(); r++) {
                    Objet aRetirer = voisin.get(rnd.nextInt(voisin.size()));
                    voisin.remove(aRetirer);
                }

                // --- Ajout aléatoire ---
                int nbAjouts = rnd.nextInt(t) + 1;
                for (int a = 0; a < nbAjouts; a++) {
                    Objet candidat = instance.getObjets()
                            .get(rnd.nextInt(instance.getObjets().size()));
                    if (!voisin.contains(candidat)) {
                        voisin.add(candidat);
                    }
                }

                // Vérification admissibilité
                if (!instance.estAdmissible(voisin)) {
                    continue;
                }

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

            solution = meilleurVoisin;
            utiliteCourante = meilleureUtilite;
        }

        return solution;
    }

}
