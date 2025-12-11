package equipe;

/**
 * Représente un projet municipal proposé par un expert.
 *
 * <p>
 * Un projet appartient à un secteur et doit être évalué selon
 * trois types de coûts (économique, social, environnemental)
 * ainsi qu’un bénéfice attendu pour la collectivité.  
 * Un projet n'est considéré comme <strong>complet</strong> que lorsque
 * toutes ces évaluations ont été réalisées.
 * </p>
 *
 * <p>
 * Cette classe fournit également :
 * <ul>
 *     <li>la gestion d’un état complet du projet via une classe interne</li>
 *     <li>des méthodes utilitaires facilitant l’utilisation dans les solveurs</li>
 *     <li>une protection par exceptions lorsque les valeurs manquent</li>
 * </ul>
 * </p>
 *
 * @author Yulei
 * @version 2.0
 * @since 1.0
 */
public class Projet {

    private String titre;
    private String description;
    private Secteur secteur;

    private Integer benefice;
    private Integer coutEconomique;
    private Integer coutSocial;
    private Integer coutEnvironnemental;

    /**
     * Classe interne immuable représentant l’état complet du projet.
     * Elle permet de regrouper les valeurs validées en un seul objet.
     */
    public static class EtatProjet {
        public final int benefice;
        public final int eco;
        public final int social;
        public final int env;

        public EtatProjet(int b, int e, int s, int en) {
            this.benefice = b;
            this.eco = e;
            this.social = s;
            this.env = en;
        }

        @Override
        public String toString() {
            return "[benef=" + benefice +
                    ", eco=" + eco +
                    ", social=" + social +
                    ", env=" + env + "]";
        }
    }

    /**
     * Construit un projet dont les évaluations ne sont pas encore renseignées.
     *
     * @param titre       titre du projet
     * @param description description courte
     * @param secteur     secteur d'activité
     */
    public Projet(String titre, String description, Secteur secteur) {
        this.titre = titre;
        this.description = description;
        this.secteur = secteur;
    }

    // Getters simples
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Secteur getSecteur() { return secteur; }

    public Integer getBenefice() { return benefice; }
    public Integer getCoutEconomique() { return coutEconomique; }
    public Integer getCoutSocial() { return coutSocial; }
    public Integer getCoutEnvironnemental() { return coutEnvironnemental; }

    // Setters
    public void setBenefice(int benefice) { this.benefice = benefice; }
    public void setCoutEconomique(int coutEconomique) { this.coutEconomique = coutEconomique; }
    public void setCoutSocial(int coutSocial) { this.coutSocial = coutSocial; }
    public void setCoutEnvironnemental(int coutEnvironnemental) { this.coutEnvironnemental = coutEnvironnemental; }

    /**
     * Vérifie si toutes les évaluations ont été renseignées.
     *
     * @return true si le projet est complet
     */
    public boolean estComplet() {
        return benefice != null &&
               coutEconomique != null &&
               coutSocial != null &&
               coutEnvironnemental != null;
    }

    /**
     * Retourne l’état complet du projet.
     *
     * @return un objet contenant les valeurs validées
     * @throws IllegalStateException si le projet est incomplet
     */
    public EtatProjet getEtatComplet() {
        if (!estComplet()) {
            throw new IllegalStateException(
                "Impossible de récupérer l'état complet : projet incomplet.");
        }
        return new EtatProjet(benefice, coutEconomique, coutSocial, coutEnvironnemental);
    }

    /**
     * Définit un coût en fonction de son type.
     *
     * @param type   type de coût (éco / social / env.)
     * @param valeur valeur associée
     */
    public void setCout(TypeCout type, int valeur) {
        switch (type) {
            case ECONOMIQUE -> this.coutEconomique = valeur;
            case SOCIAL -> this.coutSocial = valeur;
            case ENVIRONNEMENTAL -> this.coutEnvironnemental = valeur;
        }
    }

    /**
     * @return la somme des trois coûts du projet
     * @throws IllegalStateException si une valeur manque
     */
    public int coutTotal() {
        if (!estComplet()) {
            throw new IllegalStateException(
                "Impossible de calculer le coût total : projet incomplet.");
        }
        return coutEconomique + coutSocial + coutEnvironnemental;
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
