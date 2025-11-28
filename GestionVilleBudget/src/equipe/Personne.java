package equipe;

/**
 * Classe abstraite représentant une personne travaillant à la mairie.
 *
 * Elle définit les attributs communs :
 * - nom
 * - prénom
 * - âge
 *
 * Cette classe ne peut pas être instanciée directement.
 */
public abstract class Personne {

    protected String nom;
    protected String prenom;
    protected int age;

    public Personne(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + age + " ans)";
    }
}
