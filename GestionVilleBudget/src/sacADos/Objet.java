package sacADos;

import java.util.Arrays;

/**
 * Représente un objet du sac à dos multidimensionnel.
 * Chaque objet possède :
 *  - une utilité (valeur)
 *  - un tableau de coûts (un coût par dimension)
 */
public class Objet {

    private int utilite;
    private int[] couts;  // tableau contenant k coûts

    public Objet(int utilite, int[] couts) {
        this.utilite = utilite;
        this.couts = couts;
    }

    public int getUtilite() {
        return utilite;
    }

    public int[] getCouts() {
        return couts;
    }

    @Override
    public String toString() {
        return "Objet{ utilite=" + utilite + ", couts=" + Arrays.toString(couts) + " }";
    }
}
