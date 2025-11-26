package equipe;

import java.util.Comparator;

/**
 * Représente un évaluateur spécialisé dans un type de coût.
 *
 * <p>
 * ⚠️ Améliorations pédagogiques :
 * <ul>
 *   <li><b>TP7</b> : gestion d'exceptions si le projet est invalide</li>
 *   <li><b>TP8</b> : ajout de comparateurs internes (Comparator)</li>
 *   <li><b>TP9</b> : création d'une classe interne (log) pour stocker les évaluations</li>
 * </ul>
 * </p>
 *
 * @author …
 * @version 2.0
 * @see TypeCout
 * @see Projet
 */
public class Evaluateur extends Personne {

    /** Type de coût évalué par cet évaluateur */
    private TypeCout specialisation;

    /**
     * Classe interne représentant un log d'évaluation.
     * <p><b>(Utilisation du concept de classes internes — TP9)</b></p>
     */
    public static class EvaluationLog {
        private Projet projet;
        private int cout;

        public EvaluationLog(Projet p, int cout) {
            this.projet = p;
            this.cout = cout;
        }

        public Projet getProjet() { return projet; }
        public int getCout() { return cout; }

        @Override
        public String toString() {
            return "[Projet=" + projet.getTitre() + ", cout=" + cout + "]";
        }
    }

    /**
     * Constructeur
     * @param nom nom de l'évaluateur
     * @param prenom prénom
     * @param age âge
     * @param specialisation type de coût évalué
     */
    public Evaluateur(String nom, String prenom, int age, TypeCout specialisation) {
        super(nom, prenom, age);
        this.specialisation = specialisation;
    }

    public TypeCout getSpecialisation() {
        return specialisation;
    }

    /**
     * Évalue le coût d'un projet.
     *
     * <p><b>(TP7 — gestion d'exceptions)</b><br>
     * Lance une IllegalArgumentException si le projet est null.
     * </p>
     *
     * <p><b>(TP9 — retourne une classe interne EvaluationLog)</b></p>
     *
     * @param projet projet à évaluer
     * @return un log contenant le projet et le coût attribué
     */
    public EvaluationLog evaluerCout(Projet projet) {

        if (projet == null) {
            throw new IllegalArgumentException(
                "Projet null — impossible d’évaluer (TP7 : gestion d’exceptions).");
        }

        int coutGenere = genererCoutAleatoire();

        switch (specialisation) {
            case ECONOMIQUE -> projet.setCoutEconomique(coutGenere);
            case SOCIAL -> projet.setCoutSocial(coutGenere);
            case ENVIRONNEMENTAL -> projet.setCoutEnvironnemental(coutGenere);
        }

        return new EvaluationLog(projet, coutGenere);
    }

    /**
     * Génère un coût aléatoire.
     * Valeur entre 10 000 et 99 999.
     */
    private int genererCoutAleatoire() {
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

    /**
     * Comparator par âge.
     * <p><b>(TP8 — utilisation de Comparator et lambdas)</b></p>
     */
    public static final Comparator<Evaluateur> COMPARE_BY_AGE =
            (a, b) -> Integer.compare(a.getAge(), b.getAge());

    /**
     * Comparator par type de spécialisation.
     * <p><b>(TP8 — tri à l’aide d’un Comparator interne)</b></p>
     */
    public static final Comparator<Evaluateur> COMPARE_BY_SPEC =
            (a, b) -> a.getSpecialisation().compareTo(b.getSpecialisation());
}
