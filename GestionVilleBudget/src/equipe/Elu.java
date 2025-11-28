package equipe;

/**
 * Représente l'élu(e) de la municipalité.
 *
 * L'élu :
 *  - est une Personne (classe abstraite)
 *  - attribue un bénéfice à chaque projet
 *  - utilise un processus stochastique simple
 */
public class Elu extends Personne {

    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }

    /**
     * Évalue le bénéfice d’un projet et l’enregistre dans celui-ci.
     *
     * @param projet projet à évaluer
     */
    public void evaluerBenefice(Projet projet) {

        if (projet == null)
            throw new IllegalArgumentException("Projet null.");

        int benefice = genererBeneficeAleatoire();
        projet.setBenefice(benefice);
    }

    /** Génère un bénéfice aléatoire */
    private int genererBeneficeAleatoire() {
        return 50_000 + (int)(Math.random() * 150_000);
    }

    @Override
    public String toString() {
        return super.toString() + " - Élu(e)";
    }
}
