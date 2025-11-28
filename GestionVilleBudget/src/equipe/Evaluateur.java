package equipe;

/**
 * Représente un évaluateur spécialisé dans un type de coût.
 *
 * Un évaluateur :
 *  - est une Personne (classe abstraite)
 *  - possède une spécialisation (économique, social, environnemental)
 *  - peut attribuer un coût à un projet
 */
public class Evaluateur extends Personne {

    /** Type de coût évalué par cet évaluateur */
    private TypeCout specialisation;

    public Evaluateur(String nom, String prenom, int age, TypeCout specialisation) {
        super(nom, prenom, age);
        this.specialisation = specialisation;
    }

    public TypeCout getSpecialisation() {
        return specialisation;
    }

    /**
     * Évalue le coût d’un projet selon la spécialisation.
     * Cette méthode génère un coût aléatoire.
     *
     * @param projet projet à évaluer
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

    /** Génère un coût aléatoire */
    private int genererCoutAleatoire() {
        return 10_000 + (int)(Math.random() * 90_000);
    }


    @Override
    public String toString() {
        return super.toString() + " - Évaluateur (" + specialisation + ")";
    }
}
