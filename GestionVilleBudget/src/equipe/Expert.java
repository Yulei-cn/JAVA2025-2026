package equipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Représente un expert capable de proposer des projets dans ses secteurs de compétence.
 */
public class Expert extends Personne {
    
    // === Attributs ===
    private Set<Secteur> secteursCompetence;  // Les secteurs dans lesquels l'expert peut proposer des projets
    private Random random;  // Pour la génération stochastique
    
    // === Constructeur ===
    public Expert(String nom, String prenom, int age, Set<Secteur> secteursCompetence) {
        super(nom, prenom, age);
        this.secteursCompetence = secteursCompetence;
        this.random = new Random();
    }
    
    // === Getter ===
    public Set<Secteur> getSecteursCompetence() {
        return secteursCompetence;
    }
    
    // === Méthode principale ===
    /**
     * Propose un certain nombre de projets dans les secteurs de compétence de l'expert.
     * 
     * @param nombreProjets Le nombre de projets à proposer
     * @return Une liste de projets proposés (non encore évalués)
     */
    public List<Projet> proposerProjets(int nombreProjets) {
        List<Projet> projetsProposés = new ArrayList<>();
        
        for (int i = 0; i < nombreProjets; i++) {
            Projet projet = creerProjetAleatoire();
            projetsProposés.add(projet);
        }
        
        return projetsProposés;
    }
    
    /**
     * Crée un projet aléatoire dans un des secteurs de compétence de l'expert.
     */
    private Projet creerProjetAleatoire() {
        // Choisir un secteur aléatoire parmi les compétences
        Secteur secteurChoisi = choisirSecteurAleatoire();
        
        // Générer un titre et une description
        String titre = genererTitre(secteurChoisi);
        String description = genererDescription(secteurChoisi);
        
        // Créer et retourner le projet (coûts et bénéfice seront évalués plus tard)
        return new Projet(titre, description, secteurChoisi);
    }
    
    /**
     * Choisit un secteur aléatoire parmi les compétences de l'expert.
     */
    private Secteur choisirSecteurAleatoire() {
        List<Secteur> secteursList = new ArrayList<>(secteursCompetence);
        return secteursList.get(random.nextInt(secteursList.size()));
    }
    
    /**
     * Génère un titre de projet selon le secteur.
     */
    private String genererTitre(Secteur secteur) {
        String[] prefixes;
        
        switch (secteur) {
            case SPORT:
                prefixes = new String[]{"Stade", "Piscine", "Gymnase", "Terrain de sport", "Centre sportif"};
                break;
            case SANTE:
                prefixes = new String[]{"Centre médical", "Hôpital", "Clinique", "Dispensaire", "Maison de santé"};
                break;
            case EDUCATION:
                prefixes = new String[]{"École", "Collège", "Lycée", "Bibliothèque", "Centre de formation"};
                break;
            case CULTURE:
                prefixes = new String[]{"Musée", "Théâtre", "Cinéma", "Centre culturel", "Médiathèque"};
                break;
            case ATTRACTIVITE_ECONOMIQUE:
                prefixes = new String[]{"Zone commerciale", "Incubateur", "Parc d'activités", "Centre d'affaires", "Technopole"};
                break;
            default:
                prefixes = new String[]{"Projet"};
        }
        
        String prefix = prefixes[random.nextInt(prefixes.length)];
        return prefix + " de Dauphine City #" + random.nextInt(1000);
    }
    
    /**
     * Génère une description de projet.
     */
    private String genererDescription(Secteur secteur) {
        return "Projet innovant dans le secteur " + secteur + 
               " visant à améliorer la vie des Dauphinois.e.s";
    }
    
    @Override
    public void afficherRole() {
        System.out.println("Expert dans les secteurs : " + secteursCompetence);
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Expert " + secteursCompetence;
    }
}