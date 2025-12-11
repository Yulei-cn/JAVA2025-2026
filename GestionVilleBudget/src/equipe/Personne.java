package equipe;

/**
 * Représente une personne travaillant au sein de la mairie.
 *
 * <p>
 * Cette classe abstraite définit les informations communes :
 * <ul>
 *     <li>nom</li>
 *     <li>prénom</li>
 *     <li>âge</li>
 * </ul>
 * Elle constitue la super-classe des différents rôles municipaux :
 * <code>Élu</code>, <code>Evaluateur</code> et <code>Expert</code>.
 * </p>
 *
 * <p>
 * Cette classe ne peut pas être instanciée directement.
 * </p>
 *
 * @author ZHU YULEI
 * @version 2.0
 * @since 1.0
 */
public abstract class Personne {

    /** Nom de la personne */
    protected String nom;

    /** Prénom de la personne */
    protected String prenom;

    /** Âge de la personne */
    protected int age;

    /**
     * Construit une nouvelle personne (classe abstraite).
     *
     * @param nom     nom de la personne
     * @param prenom  prénom de la personne
     * @param age     âge de la personne
     */
    public Personne(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    /** @return le nom de la personne */
    public String getNom() {
        return nom;
    }

    /** @return le prénom de la personne */
    public String getPrenom() {
        return prenom;
    }

    /** @return l'âge de la personne */
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + age + " ans)";
    }
}
