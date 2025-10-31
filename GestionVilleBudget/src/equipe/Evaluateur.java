package equipe;

/**
 * Représente un évaluateur spécialisé dans l'évaluation d'un type de coût.
 * Un évaluateur ne peut évaluer qu'un seul type de coût (économique, social ou environnemental).
 */
public class Evaluateur extends Personne {
    
    // === Attributs ===
    private TypeCout specialisation;  // Le type de coût que cet évaluateur évalue
    
    // === Constructeur ===
    public Evaluateur(String nom, String prenom, int age, TypeCout specialisation) {
        super(nom, prenom, age);
        this.specialisation = specialisation;
    }
    
    // === Getter ===
    public TypeCout getSpecialisation() {
        return specialisation;
    }
    
    // === Méthode principale ===
    /**
     * Évalue le coût d'un projet selon la spécialisation de cet évaluateur.
     * Génère un coût aléatoire (processus stochastique).
     * 
     * @param projet Le projet à évaluer
     */
    public void evaluerCout(Projet projet) {
        // Génération stochastique d'un coût (entre 10000 et 100000 par exemple)
        int coutGenere = genererCoutAleatoire();
        
        // Attribuer le coût selon la spécialisation
        switch (specialisation) {
            case ECONOMIQUE:
                projet.setCoutEconomique(coutGenere);
                break;
            case SOCIAL:
                projet.setCoutSocial(coutGenere);
                break;
            case ENVIRONNEMENTAL:
                projet.setCoutEnvironnemental(coutGenere);
                break;
        }
    }
    
    /**
     * Génère un coût aléatoire (processus stochastique).
     * Vous pouvez modifier cette méthode pour utiliser différentes distributions.
     */
    private int genererCoutAleatoire() {
        // Coût entre 10 000 et 100 000
        return 10000 + (int) (Math.random() * 90000);
    }
    
    @Override
    public void afficherRole() {
        System.out.println("Évaluateur spécialisé en coût " + specialisation);
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Évaluateur (" + specialisation + ")";
    }
}