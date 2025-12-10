package equipe;

/**
 * Représente un projet municipal proposé par un expert.
 *
 * Améliorations pédagogiques intégrées :
 *     TP7 : exceptions si tentative d'accès à un projet incomplet</li>
 *     TP8 : ajout de méthodes utilitaires facilitant l'usage de comparateurs</li>
 *     TP9 : ajout d'une classe interne permettant de stocker l’état du projet</li>
 *
 * Un projet n'est considéré comme complet que lorsque tous ses coûts et son
 * bénéfice ont été évalués.
 *
 * @author Yulei
 * @version 2.0
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
     * Petite classe interne enregistrant l’état complet du projet.
     * (TP9 — classes internes)
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
     * Constructeur d’un projet (non encore évalué).
     */
    public Projet(String titre, String description, Secteur secteur) {
        this.titre = titre;
        this.description = description;
        this.secteur = secteur;
    }

    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Secteur getSecteur() { return secteur; }

    public Integer getBenefice() { return benefice; }
    public Integer getCoutEconomique() { return coutEconomique; }
    public Integer getCoutSocial() { return coutSocial; }
    public Integer getCoutEnvironnemental() { return coutEnvironnemental; }

    public void setBenefice(int benefice) { this.benefice = benefice; }
    public void setCoutEconomique(int coutEconomique) { this.coutEconomique = coutEconomique; }
    public void setCoutSocial(int coutSocial) { this.coutSocial = coutSocial; }
    public void setCoutEnvironnemental(int coutEnvironnemental) { this.coutEnvironnemental = coutEnvironnemental; }

    /**
     * Détermine si le projet a reçu toutes ses évaluations.
     */
    public boolean estComplet() {
        return benefice != null &&
               coutEconomique != null &&
               coutSocial != null &&
               coutEnvironnemental != null;
    }

    /**
     * Retourne un état complet du projet.
     *
     * (TP7) : exception si projet incomplet.
     * (TP9) : retour d’une classe interne.
     *
     * @return l’état complet du projet
     */
    public EtatProjet getEtatComplet() {
        if (!estComplet()) {
            throw new IllegalStateException(
                "Projet incomplet — évaluation manquante (TP7 : gestion d'exceptions).");
        }
        return new EtatProjet(benefice, coutEconomique, coutSocial, coutEnvironnemental);
    }

    /**
     * Attribue un coût selon son type.
     */
    public void setCout(TypeCout type, int valeur) {
        switch (type) {
            case ECONOMIQUE -> this.coutEconomique = valeur;
            case SOCIAL -> this.coutSocial = valeur;
            case ENVIRONNEMENTAL -> this.coutEnvironnemental = valeur;
        }
    }

    /**
     * Retourne une version simplifiée pour les solveurs.
     * (TP8) : utile pour les comparateurs et le sac à dos.
     */
    public int coutTotal() {
        if (!estComplet())
            throw new IllegalStateException("Impossible de calculer un coût total — projet incomplet (TP7).");

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
