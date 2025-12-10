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
 * Le voisinage est défini par :
 * S' = (S \ E) ∪ A
 * avec |E| ≤ t et |A| ≤ t.
 */
public class HillClimbingSolver {
	/**
	 * Version simplifiée : voisinage t = 1 et plateauMoves = 0
	 */
	public List<Objet> resoudre(SacADos instance, List<Objet> solutionInitiale) {
	    return resoudre(instance, solutionInitiale, 1, 0);
	}


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

while (amelioration) {
amelioration = false;

List<Objet> meilleurVoisin = solution;
int meilleureUtilite = utiliteCourante;

int plateauMoves = maxPlateauMoves;

// === Génération du voisinage ===
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

}
