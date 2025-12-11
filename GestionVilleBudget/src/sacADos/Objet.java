package sacADos;

import java.util.Arrays;

/**
 * Représente un objet pour le problème du sac à dos multidimensionnel.
 *
 * <p>
 * Chaque objet possède :
 * <ul>
 *   <li>une utilité (valeur ou bénéfice)</li>
 *   <li>un tableau de coûts représentant la consommation dans chaque dimension</li>
 *   <li>un label optionnel permettant d'afficher un nom lisible (ex : un projet)</li>
 * </ul>
 * </p>
 *
 * @author VotreNom
 * @version 5.0
 * @since TP7
 */
public class Objet {

    /** Nom lisible de l'objet (optionnel, peut être null). */
    private String label;

    /** Utilité (ou valeur) de l’objet. */
    private int utilite;

    /** Coûts multidimensionnels selon les contraintes du sac à dos. */
    private int[] couts;

    /**
     * Construit un objet sans label.
     *
     * @param utilite  utilité de l’objet (positive)
     * @param couts    tableau des coûts (aucune valeur ne doit être négative)
     */
    public Objet(int utilite, int[] couts) {
        this(null, utilite, couts);
    }

    /**
     * Construit un objet avec un label.
     *
     * @param label    nom lisible (ex : nom d’un projet)
     * @param utilite  utilité de l’objet
     * @param couts    tableau des coûts
     */
    public Objet(String label, int utilite, int[] couts) {
        this.label = label;
        this.utilite = utilite;
        this.couts = couts;
    }

    /**
     * Retourne l’utilité de l’objet.
     *
     * @return utilité (valeur)
     */
    public int getUtilite() {
        return utilite;
    }

    /**
     * Retourne le tableau des coûts multidimensionnels.
     *
     * @return coûts dans chaque dimension
     */
    public int[] getCouts() {
        return couts;
    }

    /**
     * Retourne le label si disponible, sinon chaîne vide.
     *
     * @return label ou ""
     */
    public String getLabel() {
        return (label == null ? "" : label);
    }

    @Override
    public String toString() {
        String nom = (label == null ? "Objet" : label);
        return nom + " { utilite=" + utilite +
               ", couts=" + Arrays.toString(couts) + " }";
    }
}
