package equipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Représente un expert capable de proposer des projets
 * dans ses secteurs de compétence.
 */
public class Expert extends Personne {

    private final Set<Secteur> secteursCompetence;
    private static final Random RANDOM = new Random();

    public Expert(String nom, String prenom, int age, Set<Secteur> secteursCompetence) {
        super(nom, prenom, age);
        this.secteursCompetence = secteursCompetence;
    }

    public Set<Secteur> getSecteursCompetence() {
        return secteursCompetence;
    }

    /**
     * Propose un certain nombre de projets (non évalués).
     */
    public List<Projet> proposerProjets(int nombre) {
        List<Projet> projets = new ArrayList<>();

        for (int i = 0; i < nombre; i++) {
            projets.add(creerProjetAleatoire());
        }

        return projets;
    }

    /** Crée un projet aléatoire dans un des secteurs maîtrisés */
    private Projet creerProjetAleatoire() {

        Secteur s = choisirSecteurAleatoire();

        String titre = "Projet " + s + " #" + RANDOM.nextInt(1000);
        String description = "Projet dans le secteur " + s;

        return new Projet(titre, description, s);
    }

    /** Choix aléatoire d’un secteur parmi les compétences */
    private Secteur choisirSecteurAleatoire() {
        List<Secteur> liste = new ArrayList<>(secteursCompetence);
        return liste.get(RANDOM.nextInt(liste.size()));
    }


    @Override
    public String toString() {
        return super.toString() + " - Expert " + secteursCompetence;
    }
}
