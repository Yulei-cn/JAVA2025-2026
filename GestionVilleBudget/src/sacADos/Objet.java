package sacADos;

import java.util.Arrays;

/**
 * Objet utilisé dans le sac-à-dos multidimensionnel.
 * Peut contenir un label (nom du projet) pour l'affichage.
 */
public class Objet {

    private String label;   // nom du projet (facultatif)
    private int utilite;
    private int[] couts;

    /** Constructeur sans label (ancienne version) */
    public Objet(int utilite, int[] couts) {
        this.utilite = utilite;
        this.couts = couts;
        this.label = "Objet";   // valeur par défaut
    }

    /** Constructeur avec label (nouvelle version) */
    public Objet(String label, int utilite, int[] couts) {
        this.label = label;
        this.utilite = utilite;
        this.couts = couts;
    }

    public String getLabel() { return label; }
    public int getUtilite() { return utilite; }
    public int[] getCouts() { return couts; }

    @Override
    public String toString() {
        return label + " { utilite=" + utilite +
                ", couts=" + Arrays.toString(couts) + " }";
    }
}
