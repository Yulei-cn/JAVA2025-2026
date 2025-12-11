package equipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gère l’ensemble des acteurs municipaux impliqués dans la création
 * et l'évaluation des projets de Dauphine City.
 *
 * <p>
 * Une équipe municipale est composée de :
 * <ul>
 *     <li>1 élu chargé d’évaluer le bénéfice</li>
 *     <li>3 évaluateurs spécialisés (économique, social, environnemental)</li>
 *     <li>plusieurs experts capables de proposer des projets</li>
 * </ul>
 * Les projets proposés sont évalués et, lorsqu’ils sont complets, ajoutés
 * à la liste des projets étudiés.
 * </p>
 *
 * <p>
 * Cette classe illustre l’utilisation conjointe :
 * <ul>
 *     <li>d’une classe abstraite {@link Personne}</li>
 *     <li>d’objets spécialisés (Élu, Évaluateur, Expert)</li>
 *     <li>d’un cycle de simulation orchestrant la collaboration entre rôles</li>
 * </ul>
 * </p>
 *
 * @author Yulei
 * @version 2.0
 * @since 1.0
 */
public class EquipeMunicipale {

    /** L'élu chargé d’attribuer un bénéfice aux projets */
    private final Elu elu;

    /** Trois évaluateurs indexés par leur type de coût */
    private final Map<TypeCout, Evaluateur> evaluateurs;

    /** Liste des experts capables de proposer des projets */
    private final List<Expert> experts;

    /** Projets complètement évalués et validés */
    private final List<Projet> projetsEtudies;

    /**
     * Construit une équipe municipale complète, comprenant :
     * <ul>
     *     <li>un élu unique</li>
     *     <li>exactement trois évaluateurs spécialisés</li>
     *     <li>une liste d'experts</li>
     * </ul>
     *
     * @param elu               élu unique de la municipalité
     * @param listeEvaluateurs  liste contenant exactement trois évaluateurs
     * @param experts           liste des experts municipaux
     *
     * @throws IllegalArgumentException si un des rôles est absent
     * @throws IllegalArgumentException si deux évaluateurs partagent la même spécialisation
     */
    public EquipeMunicipale(Elu elu, List<Evaluateur> listeEvaluateurs, List<Expert> experts) {

        if (elu == null)
            throw new IllegalArgumentException("L'élu ne doit pas être null.");

        if (listeEvaluateurs == null || listeEvaluateurs.size() != 3)
            throw new IllegalArgumentException("Il faut exactement 3 évaluateurs.");

        if (experts == null)
            throw new IllegalArgumentException("La liste des experts ne doit pas être null.");

        this.elu = elu;
        this.experts = new ArrayList<>(experts);
        this.projetsEtudies = new ArrayList<>();
        this.evaluateurs = new HashMap<>();

        for (Evaluateur eval : listeEvaluateurs) {
            TypeCout t = eval.getSpecialisation();

            if (this.evaluateurs.containsKey(t))
                throw new IllegalArgumentException("Deux évaluateurs ont la même spécialisation : " + t);

            this.evaluateurs.put(t, eval);
        }

        // Vérification que tous les types de coûts sont présents
        if (!evaluateurs.containsKey(TypeCout.ECONOMIQUE)
                || !evaluateurs.containsKey(TypeCout.SOCIAL)
                || !evaluateurs.containsKey(TypeCout.ENVIRONNEMENTAL)) {
            throw new IllegalArgumentException("Les trois types de coûts doivent être représentés.");
        }
    }

    // ==========================================================
    //                 MÉTHODE PRINCIPALE DE SIMULATION
    // ==========================================================

    /**
     * Exécute un cycle complet de simulation :
     * <ol>
     *     <li>Chaque expert propose des projets</li>
     *     <li>Chaque évaluateur attribue son coût</li>
     *     <li>L’élu attribue un bénéfice</li>
     *     <li>Les projets entièrement évalués sont ajoutés aux projets étudiés</li>
     * </ol>
     *
     * @param nbProjetsParExpert nombre de projets proposés par expert
     *
     * @throws IllegalArgumentException si nbProjetsParExpert ≤ 0
     * @throws IllegalStateException si aucun expert n'est présent
     */
    public void executerCycleSimulation(int nbProjetsParExpert) {

        if (nbProjetsParExpert <= 0)
            throw new IllegalArgumentException("Le nombre de projets doit être > 0.");

        if (experts.isEmpty())
            throw new IllegalStateException("Impossible de simuler : aucun expert dans l'équipe.");

        List<Projet> nouveauxProjets = new ArrayList<>();

        // 1 — Projets proposés
        for (Expert expert : experts) {
            nouveauxProjets.addAll(expert.proposerProjets(nbProjetsParExpert));
        }

        // 2 + 3 — Évaluations
        for (Projet p : nouveauxProjets) {

            // Évaluateurs
            for (Map.Entry<TypeCout, Evaluateur> entry : evaluateurs.entrySet()) {
                entry.getValue().evaluerCout(p);
            }

            // Élu
            elu.evaluerBenefice(p);

            // Ajout si complet
            if (p.estComplet()) {
                projetsEtudies.add(p);
            }
        }
    }

    // ==========================================================
    //                           AFFICHAGE
    // ==========================================================

    /**
     * Affiche les membres de l'équipe municipale.
     */
    public void afficherEquipe() {
        System.out.println("\n===== Équipe municipale =====");
        System.out.println("[ÉLU]      " + elu);

        System.out.println("\n[ÉVALUATEURS]");
        for (Evaluateur e : evaluateurs.values())
            System.out.println("  - " + e);

        System.out.println("\n[EXPERTS]");
        if (experts.isEmpty()) System.out.println("  (aucun expert)");
        for (Expert ex : experts) System.out.println("  - " + ex);
    }

    /**
     * Affiche la liste des projets entièrement étudiés.
     */
    public void afficherProjets() {
        System.out.println("\n===== Projets étudiés =====");
        if (projetsEtudies.isEmpty()) {
            System.out.println("(aucun projet)");
        } else {
            int i = 1;
            for (Projet p : projetsEtudies) {
                System.out.println(i++ + ". " + p);
            }
        }
    }

    // ==========================================================
    //                          GETTERS
    // ==========================================================

    /** @return l’élu municipal */
    public Elu getElu() {
        return elu;
    }

    /** @return la liste des experts (lecture seule) */
    public List<Expert> getExperts() {
        return List.copyOf(experts);
    }

    /** @return les évaluateurs indexés par type de coût (lecture seule) */
    public Map<TypeCout, Evaluateur> getEvaluateurs() {
        return Map.copyOf(evaluateurs);
    }

    /** @return la liste des projets étudiés (lecture seule) */
    public List<Projet> getProjetsEtudies() {
        return List.copyOf(projetsEtudies);
    }
}
