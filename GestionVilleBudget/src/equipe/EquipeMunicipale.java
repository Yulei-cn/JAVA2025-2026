package equipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente l'équipe municipale de Dauphine City.
 *
 * Cette classe implémente strictement les exigences du projet :
 * - 1 élu
 * - 3 évaluateurs (un par type de coût)
 * - une liste d'experts
 * - un cycle de simulation permettant de générer et d'évaluer des projets
 *
 * Aucun ajout inutile : pas de logs, pas de comparateurs, pas de tri.
 *
 * Cette classe illustre l'utilisation :
 * - d'une classe abstraite (Personne)
 * - d'interfaces comme contrat (ProposeProjet, EvalueCout, EvalueBenefice)
 */
public class EquipeMunicipale {

    /** L'élu(e) de la municipalité (contrat : EvalueBenefice) */
    private final Elu elu;

    /** Les trois évaluateurs, indexés par leur type de coût */
    private final Map<TypeCout, Evaluateur> evaluateurs;

    /** Liste des experts capables de proposer des projets */
    private final List<Expert> experts;

    /** Projets complètement évalués */
    private final List<Projet> projetsEtudies;

    /**
     * Construit une équipe municipale complète.
     *
     * @param elu élu unique
     * @param listeEvaluateurs liste contenant exactement 3 évaluateurs
     * @param experts liste des experts
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

        // Vérification que tous les types sont couverts
        if (!evaluateurs.containsKey(TypeCout.ECONOMIQUE) ||
            !evaluateurs.containsKey(TypeCout.SOCIAL) ||
            !evaluateurs.containsKey(TypeCout.ENVIRONNEMENTAL)) {
            throw new IllegalArgumentException("Les trois types de coûts doivent être représentés.");
        }
    }

    // ==========================================================
    //                     MÉTHODE PRINCIPALE
    // ==========================================================

    /**
     * Exécute un cycle complet de simulation :
     * 1) Les experts proposent les projets
     * 2) Les évaluateurs évaluent les coûts
     * 3) L'élu attribue le bénéfice
     * 4) Les projets complets sont ajoutés à projetsEtudies
     *
     * @param nbProjetsParExpert nombre de projets proposés par expert
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

        // 2 — Évaluation des coûts + 3 — Évaluation du bénéfice
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
    //                        AFFICHAGES
    // ==========================================================

    /** Affiche les membres de l'équipe */
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

    /** Affiche les projets étudiés */
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
    //                    GETTERS (lecture seule)
    // ==========================================================

    public Elu getElu() {
        return elu;
    }

    public List<Expert> getExperts() {
        return List.copyOf(experts);
    }

    public Map<TypeCout, Evaluateur> getEvaluateurs() {
        return Map.copyOf(evaluateurs);
    }

    public List<Projet> getProjetsEtudies() {
        return List.copyOf(projetsEtudies);
    }
}
