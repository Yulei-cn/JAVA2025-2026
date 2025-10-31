package equipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Représente un expert capable de proposer des projets municipaux.
 * 
 * <p>Un expert possède des compétences dans un ou plusieurs secteurs
 * et peut proposer des projets uniquement dans ces secteurs. Les projets
 * sont générés de manière stochastique (aléatoire) lors du cycle de simulation.</p>
 * 
 * <p>Une équipe municipale peut avoir plusieurs experts.</p>
 * 
 * @author Votre Nom
 * @version 1.0
 * @see Secteur
 * @see Projet
 * @see EquipeMunicipale
 */
public class Expert extends Personne {
    
    /** Les secteurs dans lesquels cet expert peut proposer des projets */
    private Set<Secteur> secteursCompetence;
    
    /** Générateur de nombres aléatoires pour le processus stochastique */
    private Random random;
    
    /**
     * Construit un nouvel expert avec ses secteurs de compétence.
     * 
     * @param nom le nom de famille de l'expert
     * @param prenom le prénom de l'expert
     * @param age l'âge de l'expert (en années)
     * @param secteursCompetence l'ensemble des secteurs dans lesquels
     *                           l'expert peut proposer des projets
     *                           (ne doit pas être null ou vide)
     */
    public Expert(String nom, String prenom, int age, Set<Secteur> secteursCompetence) {
        super(nom, prenom, age);
        this.secteursCompetence = secteursCompetence;
        this.random = new Random();
    }
    
    /**
     * Retourne les secteurs de compétence de cet expert.
     * 
     * @return l'ensemble des secteurs dans lesquels l'expert peut proposer des projets
     */
    public Set<Secteur> getSecteursCompetence() {
        return secteursCompetence;
    }
    
    /**
     * Propose un certain nombre de projets dans les secteurs de compétence.
     * 
     * <p>Les projets sont générés de manière aléatoire. Chaque projet
     * est créé dans un des secteurs de compétence de l'expert et possède
     * un titre et une description générés automatiquement.</p>
     * 
     * <p>Note : les projets retournés ne sont pas encore évalués
     * (coûts et bénéfice à null).</p>
     * 
     * @param nombreProjets le nombre de projets à proposer (doit être > 0)
     * @return une liste de projets nouvellement créés (non évalués)
     * @see #creerProjetAleatoire()
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
     * Crée un projet aléatoire dans un des secteurs de compétence.
     * 
     * <p>Le secteur est choisi aléatoirement parmi les compétences de l'expert,
     * et le titre/description sont générés automatiquement.</p>
     * 
     * @return un nouveau projet non évalué
     * @see #choisirSecteurAleatoire()
     * @see #genererTitre(Secteur)
     * @see #genererDescription(Secteur)
     */
    private Projet creerProjetAleatoire() {
        Secteur secteurChoisi = choisirSecteurAleatoire();
        String titre = genererTitre(secteurChoisi);
        String description = genererDescription(secteurChoisi);
        
        return new Projet(titre, description, secteurChoisi);
    }
    
    /**
     * Choisit un secteur aléatoire parmi les compétences de l'expert.
     * 
     * @return un secteur choisi aléatoirement
     */
    private Secteur choisirSecteurAleatoire() {
        List<Secteur> secteursList = new ArrayList<>(secteursCompetence);
        return secteursList.get(random.nextInt(secteursList.size()));
    }
    
    /**
     * Génère un titre de projet selon le secteur.
     * 
     * <p>Le titre est choisi parmi un ensemble de préfixes
     * caractéristiques du secteur, suivi d'un numéro aléatoire.</p>
     * 
     * @param secteur le secteur du projet
     * @return un titre généré automatiquement
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
     * 
     * @param secteur le secteur du projet
     * @return une description générique du projet
     */
    private String genererDescription(Secteur secteur) {
        return "Projet innovant dans le secteur " + secteur + 
               " visant à améliorer la vie des Dauphinois.e.s";
    }
    
    /**
     * Affiche le rôle de l'expert dans l'équipe municipale.
     */
    @Override
    public void afficherRole() {
        System.out.println("Expert dans les secteurs : " + secteursCompetence);
    }
    
    /**
     * Retourne une représentation textuelle de l'expert.
     * 
     * @return une chaîne incluant le nom, l'âge et les secteurs de compétence
     */
    @Override
    public String toString() {
        return super.toString() + " - Expert " + secteursCompetence;
    }
}