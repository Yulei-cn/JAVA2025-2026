package equipe;

/**
 * Représente l'élu(e) qui évalue le bénéfice d'un projet pour la collectivité.
 */
public class Elu extends Personne {
    
    // === Constructeur ===
    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }
    
    // === Méthode principale ===
    /**
     * Évalue le bénéfice d'un projet pour la collectivité.
     * Génère un bénéfice aléatoire (processus stochastique).
     * 
     * @param projet Le projet à évaluer
     */
    public void evaluerBenefice(Projet projet) {
        int beneficeGenere = genererBeneficeAleatoire();
        projet.setBenefice(beneficeGenere);
    }
    
    /**
     * Génère un bénéfice aléatoire (processus stochastique).
     * Le bénéfice peut être proportionnel à l'importance du secteur, etc.
     */
    private int genererBeneficeAleatoire() {
        // Bénéfice entre 50 000 et 200 000
        return 50000 + (int) (Math.random() * 150000);
    }
    
    @Override
    public void afficherRole() {
        System.out.println("Élu(e) de la municipalité");
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Élu(e)";
    }
}