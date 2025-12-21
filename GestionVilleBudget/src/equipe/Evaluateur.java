package equipe;

/**
 * Évaluateur spécialisé chargé d'attribuer un coût à un projet
 * en fonction d'un type de coût particulier.
 *
 * <p>
 * Un évaluateur :
 * <ul>
 *     <li>est une {@link Personne}</li>
 *     <li>possède une spécialisation : économique, social ou environnemental</li>
 *     <li>évalue un projet en générant un coût aléatoire selon sa spécialisation</li>
 * </ul>
 *
 * @author Yulei
 * @version 2.0
 * @since 1.0
 */
public class Evaluateur extends Personne {

    /** Type de coût que cet évaluateur est chargé d'évaluer. */
    private TypeCout specialisation;

    /**
     * Construit un évaluateur spécialisé dans un type de coût.
     *
     * @param nom            nom de l'évaluateur
     * @param prenom         prénom de l'évaluateur
     * @param age            âge de l'évaluateur
     * @param specialisation type de coût pris en charge
     *
     * @throws IllegalArgumentException si la spécialisation est null
     */
    public Evaluateur(String nom, String prenom, int age, TypeCout specialisation) {
        super(nom, prenom, age);

        if (specialisation == null)
            throw new IllegalArgumentException("La spécialisation ne peut pas être null.");

        this.specialisation = specialisation;
    }

    /**
     * Retourne la spécialisation de cet évaluateur.
     *
     * @return spécialisation (économique, sociale, environnementale)
     */
    public TypeCout getSpecialisation() {
        return specialisation;
    }

    /**
     * Évalue le coût d’un projet en fonction de la spécialisation de l'évaluateur.
     *
     * <p>
     * Un coût aléatoire est généré puis affecté au projet dans le champ correspondant.
     *
     * @param projet projet à évaluer
     *
     * @throws IllegalArgumentException si le projet est null
     */
    public void evaluerCout(Projet projet) {

        if (projet == null)
            throw new IllegalArgumentException("Projet null.");

        int cout = genererCoutAleatoire();

        switch (specialisation) {
            case ECONOMIQUE -> projet.setCoutEconomique(cout);
            case SOCIAL -> projet.setCoutSocial(cout);
            case ENVIRONNEMENTAL -> projet.setCoutEnvironnemental(cout);
        }
    }

    /**
     * Génère un coût aléatoire compris entre 10 000 et 100 000.
     *
     * @return coût aléatoire
     */
    private int genererCoutAleatoire() {
        return 10_000 + (int)(Math.random() * 90_000);
    }

    @Override
    public String toString() {
        return super.toString() + " - Évaluateur (" + specialisation + ")";
    }
}
