package sacADos;

import java.util.ArrayList;
import java.util.List;

import equipe.Projet;
import equipe.Secteur;

/**
 * Classe utilitaire permettant de convertir une liste de Projet
 * (définis dans le module equipe) en objets de SacADos.
 */
public class VersSacADos {

    /**
     * Conversion des projets -> SacADos (mode 1 : budgets par types de coûts)
     *
     * Chaque projet devient un Objet :
     * utilite = benefice
     * couts   = [coutEco, coutSocial, coutEnv]
     */
    public static SacADos depuisProjetSelonCouts(List<Projet> projets, int[] budgets) {

        List<Objet> objets = new ArrayList<>();

        for (Projet p : projets) {
            int[] couts = new int[]{
                p.getCoutEconomique(),
                p.getCoutSocial(),
                p.getCoutEnvironnemental()
            };
            objets.add(new Objet(p.getBenefice(), couts));
        }

        return new SacADos(3, budgets, objets); // dimension = 3
    }


    /**
     * Conversion des projets -> SacADos (mode 2 : budgets par secteurs)
     *
     * Dimension = nb secteurs (5)
     * seul le cout économique compte ici.
     */
    public static SacADos depuisProjetSelonSecteurs(List<Projet> projets, int[] budgetsSecteurs) {

        List<Objet> objets = new ArrayList<>();

        for (Projet p : projets) {
            int[] couts = new int[5]; // 5 secteurs

            couts[p.getSecteur().ordinal()] = p.getCoutEconomique(); // coût placé dans son secteur

            objets.add(new Objet(p.getBenefice(), couts));
        }

        return new SacADos(5, budgetsSecteurs, objets);
    }
}
