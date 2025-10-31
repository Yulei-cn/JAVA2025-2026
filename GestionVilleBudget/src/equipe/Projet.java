package equipe;

/**
 * Représente un projet municipal proposé par un expert.
 * 
 * <p>Un projet est caractérisé par :
 * <ul>
 *   <li>Un titre et une description</li>
 *   <li>Un secteur d'appartenance unique</li>
 *   <li>Un bénéfice attendu pour la collectivité (évalué par l'élu)</li>
 *   <li>Trois types de coûts : économique, social et environnemental</li>
 * </ul>
 * </p>
 * 
 * <p>Un projet n'est considéré comme complet que lorsque tous ses coûts
 * et son bénéfice ont été évalués par l'équipe municipale.</p>
 * 
 * @author Votre Nom
 * @version 1.0
 * @see Secteur
 * @see TypeCout
 * @see Expert
 * @see EquipeMunicipale
 */
public class Projet {
    
    /** Le titre du projet */
    private String titre;
    
    /** La description détaillée du projet */
    private String description;
    
    /** Le secteur auquel appartient ce projet */
    private Secteur secteur;
    
    /** Le bénéfice attendu pour la collectivité (null si pas encore évalué) */
    private Integer benefice;
    
    /** Le coût économique du projet (null si pas encore évalué) */
    private Integer coutEconomique;
    
    /** Le coût social du projet (null si pas encore évalué) */
    private Integer coutSocial;
    
    /** Le coût environnemental du projet (null si pas encore évalué) */
    private Integer coutEnvironnemental;
    
    /**
     * Construit un nouveau projet (non encore évalué).
     * 
     * <p>Les coûts et le bénéfice sont initialisés à null et devront
     * être attribués par l'équipe municipale lors du cycle de simulation.</p>
     * 
     * @param titre le titre du projet (ne doit pas être null ou vide)
     * @param description la description du projet
     * @param secteur le secteur d'appartenance du projet
     */
    public Projet(String titre, String description, Secteur secteur) {
        this.titre = titre;
        this.description = description;
        this.secteur = secteur;
    }
    
    /**
     * Retourne le titre du projet.
     * 
     * @return le titre
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * Retourne la description du projet.
     * 
     * @return la description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Retourne le secteur auquel appartient ce projet.
     * 
     * @return le secteur
     */
    public Secteur getSecteur() {
        return secteur;
    }
    
    /**
     * Retourne le bénéfice attendu du projet.
     * 
     * @return le bénéfice, ou null si pas encore évalué
     */
    public Integer getBenefice() {
        return benefice;
    }
    
    /**
     * Retourne le coût économique du projet.
     * 
     * @return le coût économique, ou null si pas encore évalué
     */
    public Integer getCoutEconomique() {
        return coutEconomique;
    }
    
    /**
     * Retourne le coût social du projet.
     * 
     * @return le coût social, ou null si pas encore évalué
     */
    public Integer getCoutSocial() {
        return coutSocial;
    }
    
    /**
     * Retourne le coût environnemental du projet.
     * 
     * @return le coût environnemental, ou null si pas encore évalué
     */
    public Integer getCoutEnvironnemental() {
        return coutEnvironnemental;
    }
    
    /**
     * Attribue le bénéfice attendu du projet.
     * 
     * @param benefice le bénéfice (doit être positif)
     */
    public void setBenefice(int benefice) {
        this.benefice = benefice;
    }
    
    /**
     * Attribue le coût économique du projet.
     * 
     * @param coutEconomique le coût économique (doit être positif)
     */
    public void setCoutEconomique(int coutEconomique) {
        this.coutEconomique = coutEconomique;
    }
    
    /**
     * Attribue le coût social du projet.
     * 
     * @param coutSocial le coût social (doit être positif)
     */
    public void setCoutSocial(int coutSocial) {
        this.coutSocial = coutSocial;
    }
    
    /**
     * Attribue le coût environnemental du projet.
     * 
     * @param coutEnvironnemental le coût environnemental (doit être positif)
     */
    public void setCoutEnvironnemental(int coutEnvironnemental) {
        this.coutEnvironnemental = coutEnvironnemental;
    }
    
    /**
     * Vérifie si le projet a été complètement évalué.
     * 
     * <p>Un projet est complet si tous ses coûts et son bénéfice
     * ont été attribués (ne sont pas null).</p>
     * 
     * @return true si le projet est complet, false sinon
     */
    public boolean estComplet() {
        return benefice != null && 
               coutEconomique != null && 
               coutSocial != null && 
               coutEnvironnemental != null;
    }
    
    /**
     * Attribue un coût selon son type.
     * 
     * <p>Cette méthode est une alternative aux setters individuels,
     * permettant d'attribuer un coût de manière générique.</p>
     * 
     * @param type le type de coût à attribuer
     * @param valeur la valeur du coût (doit être positive)
     */
    public void setCout(TypeCout type, int valeur) {
        switch (type) {
            case ECONOMIQUE:
                this.coutEconomique = valeur;
                break;
            case SOCIAL:
                this.coutSocial = valeur;
                break;
            case ENVIRONNEMENTAL:
                this.coutEnvironnemental = valeur;
                break;
        }
    }
    
    /**
     * Retourne une représentation textuelle du projet.
     * 
     * @return une chaîne contenant toutes les informations du projet
     */
    @Override
    public String toString() {
        return "Projet{" +
                "titre='" + titre + '\'' +
                ", secteur=" + secteur +
                ", benefice=" + benefice +
                ", coutEco=" + coutEconomique +
                ", coutSoc=" + coutSocial +
                ", coutEnv=" + coutEnvironnemental +
                '}';
    }
}