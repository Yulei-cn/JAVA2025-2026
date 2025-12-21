package equipe;

/**
 * Représente un élu de la municipalité chargé d’évaluer le bénéfice des projets.
 *
 * <p>
 * Un élu est une {@link Personne} et dispose d’un mécanisme d’évaluation
 * simple consistant à attribuer un bénéfice estimé à chaque projet.  
 * L’évaluation repose sur une génération pseudo-aléatoire contrôlée.
 *
 * <p>
 * Cette classe assure :
 * <ul>
 *     <li>la vérification des entrées (projet non null)</li>
 *     <li>l’attribution d’un bénéfice cohérent</li>
 *     <li>un comportement indépendant pour chaque élu</li>
 * </ul>
 *
 * @author Yulei
 * @version 2.0
 * @since 1.0
 */
public class Elu extends Personne {

    /**
     * Construit un élu de la municipalité.
     *
     * @param nom     nom de l’élu
     * @param prenom  prénom de l’élu
     * @param age     âge de l’élu
     */
    public Elu(String nom, String prenom, int age) {
        super(nom, prenom, age);
    }

    /**
     * Attribue un bénéfice au projet spécifié.
     *
     * <p>
     * Le bénéfice généré est un montant compris dans une plage fixe,
     * simulant une estimation réaliste d’impact socio-économique.
     *
     * @param projet projet à évaluer
     * @throws IllegalArgumentException si {@code projet} est null
     */
    public void evaluerBenefice(Projet projet) {
        if (projet == null)
            throw new IllegalArgumentException("Impossible d'évaluer un projet null.");

        int benefice = genererBeneficeAleatoire();
        projet.setBenefice(benefice);
    }

    /**
     * Génère un bénéfice pseudo-aléatoire compris entre 50 000 et 200 000.
     *
     * @return un montant de bénéfice simulé
     */
    private int genererBeneficeAleatoire() {
        return 50_000 + (int)(Math.random() * 150_000);
    }

    @Override
    public String toString() {
        return super.toString() + " - Élu(e)";
    }
}
