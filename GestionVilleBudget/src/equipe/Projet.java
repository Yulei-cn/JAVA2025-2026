package equipe;

/**
 * Représente un projet proposé pour la municipalité.
 */
public class Projet {
    
    // === Attributs ===
    private String titre;
    private String description;
    private Secteur secteur;
    private Integer benefice;           // Integer permet null (pas encore évalué)
    private Integer coutEconomique;
    private Integer coutSocial;
    private Integer coutEnvironnemental;
    
    // === Constructeur ===
    /**
     * Constructeur pour créer un projet (avant évaluation).
     * Les coûts et le bénéfice seront attribués plus tard.
     */
    public Projet(String titre, String description, Secteur secteur) {
        this.titre = titre;
        this.description = description;
        this.secteur = secteur;
        // Les coûts et bénéfice sont null au départ
    }
    
    // === Getters ===
    public String getTitre() {
        return titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Secteur getSecteur() {
        return secteur;
    }
    
    public Integer getBenefice() {
        return benefice;
    }
    
    public Integer getCoutEconomique() {
        return coutEconomique;
    }
    
    public Integer getCoutSocial() {
        return coutSocial;
    }
    
    public Integer getCoutEnvironnemental() {
        return coutEnvironnemental;
    }
    
    // === Setters === ⬅️ 这些是关键！
    public void setBenefice(int benefice) {
        this.benefice = benefice;
    }
    
    public void setCoutEconomique(int coutEconomique) {
        this.coutEconomique = coutEconomique;
    }
    
    public void setCoutSocial(int coutSocial) {
        this.coutSocial = coutSocial;
    }
    
    public void setCoutEnvironnemental(int coutEnvironnemental) {
        this.coutEnvironnemental = coutEnvironnemental;
    }
    
    // === Méthode utile ===
    /**
     * Vérifie si le projet a été complètement évalué.
     * @return true si tous les coûts et le bénéfice ont été attribués
     */
    public boolean estComplet() {
        return benefice != null && 
               coutEconomique != null && 
               coutSocial != null && 
               coutEnvironnemental != null;
    }
    
    /**
     * Attribue un coût selon son type.
     * Méthode alternative aux setters individuels.
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