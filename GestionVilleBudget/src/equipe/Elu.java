package equipe;

/**
 * Représente l'élu(e) de la municipalité.
 * 
 * <p>L'élu(e) est responsable d'évaluer le bénéfice qu'un projet
 * apportera à la collectivité. Cette évaluation se fait via un
 * processus stochastique (génération aléatoire).</p>
 * 
 * <p>Il n'y a qu'un seul élu par équipe municipale.</p>
 * 
 * @author Votre Nom
 * @version 1.0
 * @see Projet
 * @see EquipeMunicipale
 */
public class Elu extends Personne {
    
    /**
     * Construit un nouvel élu.
     * 
     * @param nom le nom de famille de l'élu
     * @param prenom le prénom de l'élu
     * @param age l'âge de l'élu (en années)
     */
    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }
    
    /**
     * Évalue le bénéfice d'un projet pour la collectivité.
     * 
     * <p>Cette méthode génère un bénéfice de manière stochastique
     * et l'attribue au projet.</p>
     * 
     * @param projet le projet à évaluer (ne doit pas être null)
     * @see #genererBeneficeAleatoire()
     */
    public void evaluerBenefice(Projet projet) {
        int beneficeGenere = genererBeneficeAleatoire();
        projet.setBenefice(beneficeGenere);
    }
    
    /**
     * Génère un bénéfice aléatoire selon un processus stochastique.
     * 
     * <p>Le bénéfice généré se situe entre 50 000 et 200 000.</p>
     * 
     * @return un bénéfice aléatoire entre 50000 et 199999
     */
    private int genererBeneficeAleatoire() {
        return 50000 + (int) (Math.random() * 150000);
    }
    
    /**
     * Affiche le rôle de l'élu dans l'équipe municipale.
     */
    @Override
    public void afficherRole() {
        System.out.println("Élu(e) de la municipalité");
    }
    
    /**
     * Retourne une représentation textuelle de l'élu.
     * 
     * @return une chaîne incluant le nom, l'âge et le rôle
     */
    @Override
    public String toString() {
        return super.toString() + " - Élu(e)";
    }
}