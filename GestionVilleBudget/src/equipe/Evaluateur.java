package equipe;

/**
 * Représente un évaluateur spécialisé dans l'évaluation d'un type de coût spécifique.
 * 
 * <p>Chaque évaluateur est spécialisé dans l'évaluation d'un seul type de coût :
 * économique, social ou environnemental. Il utilise un processus stochastique
 * pour générer une estimation du coût d'un projet.</p>
 * 
 * @author Votre Nom
 * @version 1.0
 * @see TypeCout
 * @see Projet
 */
public class Evaluateur extends Personne {
    
    /** Le type de coût que cet évaluateur est capable d'évaluer */
    private TypeCout specialisation;
    
    /**
     * Construit un nouvel évaluateur avec une spécialisation donnée.
     * 
     * @param nom le nom de famille de l'évaluateur
     * @param prenom le prénom de l'évaluateur
     * @param age l'âge de l'évaluateur (en années)
     * @param specialisation le type de coût que cet évaluateur peut évaluer
     */
    public Evaluateur(String nom, String prenom, int age, TypeCout specialisation) {
        super(nom, prenom, age);
        this.specialisation = specialisation;
    }
    
    /**
     * Retourne le type de coût dans lequel cet évaluateur est spécialisé.
     * 
     * @return la spécialisation de l'évaluateur
     */
    public TypeCout getSpecialisation() {
        return specialisation;
    }
    
    /**
     * Évalue le coût d'un projet selon la spécialisation de cet évaluateur.
     * 
     * <p>Cette méthode génère un coût de manière stochastique (aléatoire)
     * et l'attribue au projet dans la catégorie correspondant à la
     * spécialisation de l'évaluateur.</p>
     * 
     * @param projet le projet à évaluer (ne doit pas être null)
     * @see #genererCoutAleatoire()
     */
    public void evaluerCout(Projet projet) {
        int coutGenere = genererCoutAleatoire();
        
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
     * Génère un coût aléatoire selon un processus stochastique.
     * 
     * <p>Le coût généré se situe entre 10 000 et 100 000.</p>
     * 
     * @return un coût aléatoire entre 10000 et 99999
     */
    private int genererCoutAleatoire() {
        return 10000 + (int) (Math.random() * 90000);
    }
    
    /**
     * Affiche le rôle de cet évaluateur dans l'équipe municipale.
     */
    @Override
    public void afficherRole() {
        System.out.println("Évaluateur spécialisé en coût " + specialisation);
    }
    
    /**
     * Retourne une représentation textuelle de cet évaluateur.
     * 
     * @return une chaîne incluant le nom, l'âge et la spécialisation
     */
    @Override
    public String toString() {
        return super.toString() + " - Évaluateur (" + specialisation + ")";
    }
}