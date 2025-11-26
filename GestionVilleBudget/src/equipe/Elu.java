package equipe;

/**
 * Représente l'élu(e) de la municipalité.
 *
 * <p>
 * ⚠️ Améliorations pédagogiques intégrées :
 * <ul>
 *   <li><b>TP7</b> : gestion d'exceptions si le projet est invalide</li>
 *   <li><b>TP9</b> : ajout d'une classe interne pour journaliser les évaluations</li>
 * </ul>
 * </p>
 *
 * <p>L'élu(e) est responsable d'évaluer le bénéfice attendu d'un projet
 * pour la collectivité via un processus stochastique.</p>
 *
 * @author …
 * @version 2.0
 * @see Projet
 * @see EquipeMunicipale
 */
public class Elu extends Personne {

    /**
     * Classe interne représentant un enregistrement de bénéfice.
     * <p><b>(TP9 — utilisation de classes internes)</b></p>
     */
    public static class BeneficeLog {
        private Projet projet;
        private int benefice;

        public BeneficeLog(Projet p, int b) {
            this.projet = p;
            this.benefice = b;
        }

        public Projet getProjet() { return projet; }
        public int getBenefice() { return benefice; }

        @Override
        public String toString() {
            return "[Projet=" + projet.getTitre() + ", bénéfice=" + benefice + "]";
        }
    }

    /**
     * Constructeur de l'élu(e).
     */
    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }

    /**
     * Évalue le bénéfice d'un projet.
     *
     * <p><b>(TP7 — gestion d'exceptions)</b><br>
     * Lève une IllegalArgumentException si le projet est null.
     * </p>
     *
     * <p><b>(TP9 — retourne un log interne pour journalisation)</b></p>
     *
     * @param projet projet à évaluer
     * @return un BeneficeLog contenant les informations d’évaluation
     */
    public BeneficeLog evaluerBenefice(Projet projet) {

        if (projet == null) {
            throw new IllegalArgumentException(
                "Projet null — impossible d’évaluer le bénéfice (TP7 : gestion d’exceptions).");
        }

        int beneficeGenere = genererBeneficeAleatoire();
        projet.setBenefice(beneficeGenere);

        return new BeneficeLog(projet, beneficeGenere);
    }

    /**
     * Génère un bénéfice aléatoire.
     */
    private int genererBeneficeAleatoire() {
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
