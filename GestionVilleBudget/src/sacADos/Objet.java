package sacADos;

import java.util.Arrays;

/**
 * Classe représentant un objet pour le problème du sac à dos multidimensionnel.
 *
 * <p>
 * Chaque objet est caractérisé par :
 * <ul>
 *   <li>Une utilité (ou valeur)</li>
 *   <li>Un tableau de coûts, où chaque case correspond à une dimension</li>
 * </ul>
 * </p>
 *
 * <p>
 * Cette classe est utilisée dans les algorithmes de résolution gloutons et dynamiques
 * du problème du sac à dos (référence : travaux pratiques TP7/TP8 de programmation Java).
 * </p>
 *
 * @author Vous
 * @version 1.0
 */
public class Objet {

    /** Utilité (ou valeur) de l'objet */
    private int utilite;

    /** Coûts multidimensionnels de l'objet */
    private int[] couts;

    /**
     * Construit un nouvel objet multidimensionnel.
     *
     * @param utilite  l'utilité (doit être positive)
     * @param couts    le tableau de coûts selon chaque dimension (aucune case ne doit être négative)
     */
    public Objet(int utilite, int[] couts) {
        this.utilite = utilite;
        this.couts = couts;
    }

    /**
     * Retourne l'utilité de l'objet.
     *
     * @return utilité de l'objet
     */
    public int getUtilite() {
        return utilite;
    }

    /**
     * Retourne les coûts multidimensionnels de l'objet.
     *
     * @return tableau des coûts
     */
    public int[] getCouts() {
        return couts;
    }

    @Override
    public String toString() {
        return "Objet{ utilite=" + utilite +
                ", couts=" + Arrays.toString(couts) +
                " }";
    }
}
