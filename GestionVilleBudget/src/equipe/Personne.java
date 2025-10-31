package equipe;

public abstract class Personne {

    // === Attributs ===
    protected String nom;
    protected String prenom;
    protected int age;

    // === Constructeur === ⬅️ 确保有这个！
    public Personne(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    // === Getters ===
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    // === Méthode abstraite ===
    public abstract void afficherRole();

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + age + " ans)";
    }
}