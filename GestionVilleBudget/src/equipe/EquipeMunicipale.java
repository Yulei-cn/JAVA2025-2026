package equipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente l'équipe municipale complète de Dauphine City.
 *
 * <p>
 * Intégration des notions vues en TP :
 * <ul>
 *   <li><b>TP7</b> : vérifications et gestion d'exceptions (arguments invalides, état incohérent)</li>
 *   <li><b>TP8</b> : utilisation de {@link Comparator} pour trier les projets selon différents critères</li>
 *   <li><b>TP9</b> : classe interne {@link HistoriqueEvaluation} pour tracer les évaluations</li>
 * </ul>
 * </p>
 *
 * <p>Une équipe municipale est composée de :
 * <ul>
 *   <li>un(e) élu(e) unique,</li>
 *   <li>trois évaluateurs (un par type de coût),</li>
 *   <li>une liste d'experts pouvant proposer des projets.</li>
 * </ul>
 * L'équipe est capable d'exécuter un cycle de simulation complet.
 * </p>
 */
public class EquipeMunicipale {

    /** L'élu de la municipalité */
    private final Elu elu;

    /** Les trois évaluateurs, indexés par leur type de coût */
    private final Map<TypeCout, Evaluateur> evaluateurs;

    /** La liste des experts de l'équipe */
    private final List<Expert> experts;

    /** La liste des projets complètement évalués par l'équipe */
    private final List<Projet> projetsEtudies;

    /**
     * Historique des évaluations réalisées par l'équipe.
     * <p><b>(TP9 — classe interne)</b></p>
     */
    private final List<HistoriqueEvaluation> historiqueEvaluations;

    /**
     * Classe interne représentant une entrée dans l'historique des évaluations.
     *
     * <p>Une entrée contient :
     * <ul>
     *     <li>le projet concerné,</li>
     *     <li>le nom de la personne qui a effectué l'évaluation,</li>
     *     <li>le type d'évaluation (coût économique / social / environnemental / bénéfice),</li>
     *     <li>la valeur attribuée.</li>
     * </ul>
     * </p>
     */
    public static class HistoriqueEvaluation {
        private final Projet projet;
        private final String acteur;
        private final String typeEvaluation;
        private final int valeur;

        public HistoriqueEvaluation(Projet projet, String acteur, String typeEvaluation, int valeur) {
            this.projet = projet;
            this.acteur = acteur;
            this.typeEvaluation = typeEvaluation;
            this.valeur = valeur;
        }

        public Projet getProjet() {
            return projet;
        }

        public String getActeur() {
            return acteur;
        }

        public String getTypeEvaluation() {
            return typeEvaluation;
        }

        public int getValeur() {
            return valeur;
        }

        @Override
        public String toString() {
            return "HistoriqueEvaluation{" +
                    "projet='" + projet.getTitre() + '\'' +
                    ", acteur='" + acteur + '\'' +
                    ", typeEvaluation='" + typeEvaluation + '\'' +
                    ", valeur=" + valeur +
                    '}';
        }
    }

    /**
     * Comparateur interne pour trier les projets par bénéfice décroissant.
     * <p><b>(TP8 — Comparator)</b></p>
     */
    private static final Comparator<Projet> COMPARATEUR_BENEFICE_DESC =
            (p1, p2) -> Integer.compare(
                    p2.getBenefice() != null ? p2.getBenefice() : 0,
                    p1.getBenefice() != null ? p1.getBenefice() : 0
            );

    /**
     * Comparateur interne pour trier les projets par coût total croissant.
     * <p><b>(TP8 — Comparator + méthode utilitaire coutTotal() dans Projet)</b></p>
     */
    private static final Comparator<Projet> COMPARATEUR_COUT_TOTAL_ASC =
            (p1, p2) -> {
                int c1 = p1.estComplet() ? p1.coutTotal() : Integer.MAX_VALUE;
                int c2 = p2.estComplet() ? p2.coutTotal() : Integer.MAX_VALUE;
                return Integer.compare(c1, c2);
            };

    /**
     * Construit une nouvelle équipe municipale.
     *
     * <p><b>(TP7)</b> : cette méthode vérifie la cohérence des paramètres et lève
     * des {@link IllegalArgumentException} en cas de problème.</p>
     *
     * @param elu              l'élu de la municipalité (ne doit pas être {@code null})
     * @param listeEvaluateurs la liste des évaluateurs (doit contenir exactement 3 éléments)
     * @param experts          la liste des experts (peut être vide mais pas {@code null})
     */
    public EquipeMunicipale(Elu elu, List<Evaluateur> listeEvaluateurs, List<Expert> experts) {
        if (elu == null) {
            throw new IllegalArgumentException("L'élu ne doit pas être null.");
        }
        if (listeEvaluateurs == null || listeEvaluateurs.size() != 3) {
            throw new IllegalArgumentException("Il doit y avoir exactement 3 évaluateurs (un par type de coût).");
        }
        if (experts == null) {
            throw new IllegalArgumentException("La liste des experts ne doit pas être null.");
        }

        this.elu = elu;
        this.experts = new ArrayList<>(experts);
        this.projetsEtudies = new ArrayList<>();
        this.historiqueEvaluations = new ArrayList<>();
        this.evaluateurs = new HashMap<>();

        for (Evaluateur eval : listeEvaluateurs) {
            if (eval == null) {
                throw new IllegalArgumentException("Un évaluateur dans la liste est null.");
            }
            TypeCout type = eval.getSpecialisation();
            if (this.evaluateurs.containsKey(type)) {
                throw new IllegalArgumentException("Plusieurs évaluateurs ont la même spécialisation : " + type);
            }
            this.evaluateurs.put(type, eval);
        }

        if (!evaluateurs.containsKey(TypeCout.ECONOMIQUE) ||
            !evaluateurs.containsKey(TypeCout.SOCIAL) ||
            !evaluateurs.containsKey(TypeCout.ENVIRONNEMENTAL)) {
            throw new IllegalArgumentException(
                    "Les trois types de coûts doivent être couverts : ECONOMIQUE, SOCIAL, ENVIRONNEMENTAL.");
        }
    }

    // ================== ACCESSEURS ==================

    public Elu getElu() {
        return elu;
    }

    public Map<TypeCout, Evaluateur> getEvaluateurs() {
        return Collections.unmodifiableMap(evaluateurs);
    }

    public List<Expert> getExperts() {
        return Collections.unmodifiableList(experts);
    }

    public List<Projet> getProjetsEtudies() {
        return Collections.unmodifiableList(projetsEtudies);
    }

    public List<HistoriqueEvaluation> getHistoriqueEvaluations() {
        return Collections.unmodifiableList(historiqueEvaluations);
    }

    // ================== MÉTHODES PRINCIPALES ==================

    /**
     * Exécute un cycle complet de simulation.
     *
     * <p>Étapes :
     * <ol>
     *   <li>Chaque expert propose {@code nombreProjetsParExpert} projets.</li>
     *   <li>Pour chaque projet :
     *       <ul>
     *         <li>les trois évaluateurs attribuent leurs coûts respectifs,</li>
     *         <li>l'élu attribue un bénéfice,</li>
     *         <li>si le projet est complet, il est ajouté à {@link #projetsEtudies}.</li>
     *       </ul>
     *   </li>
     * </ol>
     *
     * <p><b>(TP7)</b> : lève une {@link IllegalArgumentException} si le nombre
     * de projets par expert est invalide.</p>
     *
     * @param nombreProjetsParExpert nombre de projets à proposer par expert (doit être &gt; 0)
     */
    public void executerCycleSimulation(int nombreProjetsParExpert) {
        if (nombreProjetsParExpert <= 0) {
            throw new IllegalArgumentException("Le nombre de projets par expert doit être strictement positif.");
        }
        if (experts.isEmpty()) {
            throw new IllegalStateException("Aucun expert dans l'équipe — impossible de lancer une simulation.");
        }

        System.out.println("\n========== Début du cycle de simulation ==========");
        System.out.println("Chaque expert proposera " + nombreProjetsParExpert + " projet(s).\n");

        List<Projet> nouveauxProjets = genererProjets(nombreProjetsParExpert);
        System.out.println("\nNombre total de projets proposés : " + nouveauxProjets.size());
        System.out.println("\nÉvaluation des projets...\n");

        evaluerEtAjouterProjets(nouveauxProjets);

        System.out.println("\n========== Fin du cycle de simulation ==========");
        System.out.println("Nombre de projets valides ajoutés : " + projetsEtudies.size());
    }

    /**
     * Génère les projets proposés par chaque expert.
     */
    private List<Projet> genererProjets(int nombreProjetsParExpert) {
        List<Projet> nouveauxProjets = new ArrayList<>();
        for (Expert expert : experts) {
            System.out.println("Expert " + expert.getPrenom() + " " + expert.getNom()
                    + " propose " + nombreProjetsParExpert + " projet(s)...");
            List<Projet> projetsExpert = expert.proposerProjets(nombreProjetsParExpert);
            nouveauxProjets.addAll(projetsExpert);
        }
        return nouveauxProjets;
    }

    /**
     * Fait évaluer les projets par les évaluateurs et l'élu, et ajoute les
     * projets complets à la liste {@link #projetsEtudies}.
     */
    private void evaluerEtAjouterProjets(List<Projet> projets) {
        int compteurValides = 0;

        for (Projet projet : projets) {
            System.out.println("Évaluation du projet : " + projet.getTitre());

            // Évaluation des coûts
            for (Map.Entry<TypeCout, Evaluateur> entry : evaluateurs.entrySet()) {
                TypeCout type = entry.getKey();
                Evaluateur evaluateur = entry.getValue();
                evaluateur.evaluerCout(projet); // on ignore éventuellement la valeur de retour

                int valeur;
                switch (type) {
                    case ECONOMIQUE -> valeur = projet.getCoutEconomique();
                    case SOCIAL -> valeur = projet.getCoutSocial();
                    case ENVIRONNEMENTAL -> valeur = projet.getCoutEnvironnemental();
                    default -> valeur = -1;
                }

                historiqueEvaluations.add(
                        new HistoriqueEvaluation(
                                projet,
                                evaluateur.getPrenom() + " " + evaluateur.getNom(),
                                "COUT_" + type,
                                valeur
                        )
                );
            }

            // Évaluation du bénéfice
            elu.evaluerBenefice(projet);
            historiqueEvaluations.add(
                    new HistoriqueEvaluation(
                            projet,
                            elu.getPrenom() + " " + elu.getNom(),
                            "BENEFICE",
                            projet.getBenefice()
                    )
            );

            if (projet.estComplet()) {
                projetsEtudies.add(projet);
                compteurValides++;
                System.out.println("  ✅ Projet complet et ajouté aux projets étudiés.");
            } else {
                System.out.println("  ❌ Projet incomplet, ignoré.");
            }
        }

        System.out.println("\nNombre de projets complets dans ce cycle : " + compteurValides);
    }

    // ================== FONCTIONS D'AFFICHAGE ==================

    /**
     * Affiche les informations sur tous les membres de l'équipe municipale.
     */
    public void afficherEquipe() {
        System.out.println("\n===== Équipe municipale de Dauphine City =====");
        System.out.println("\n[ÉLU]");
        System.out.println("  " + elu);

        System.out.println("\n[ÉVALUATEURS]");
        for (Evaluateur eval : evaluateurs.values()) {
            System.out.println("  " + eval);
        }

        System.out.println("\n[EXPERTS]");
        if (experts.isEmpty()) {
            System.out.println("  (aucun expert)");
        } else {
            for (Expert expert : experts) {
                System.out.println("  " + expert);
            }
        }
        System.out.println("==============================================\n");
    }

    /**
     * Affiche tous les projets étudiés par l'équipe.
     */
    public void afficherProjets() {
        System.out.println("\n===== Projets étudiés =====");
        if (projetsEtudies.isEmpty()) {
            System.out.println("(aucun projet pour le moment)");
        } else {
            for (int i = 0; i < projetsEtudies.size(); i++) {
                System.out.println((i + 1) + ". " + projetsEtudies.get(i));
            }
        }
        System.out.println("===========================\n");
    }

    /**
     * Réinitialise la liste des projets étudiés et l'historique des évaluations.
     */
    public void reinitialiserProjets() {
        projetsEtudies.clear();
        historiqueEvaluations.clear();
        System.out.println("Les projets étudiés et l'historique des évaluations ont été réinitialisés.");
    }

    // ================== MÉTHODES AVANCÉES (TP8) ==================

    /**
     * Trie la liste des projets étudiés par bénéfice décroissant.
     * <p><b>(TP8 — utilisation de Comparator et sort)</b></p>
     */
    public void trierProjetsParBeneficeDecroissant() {
        projetsEtudies.sort(COMPARATEUR_BENEFICE_DESC);
    }

    /**
     * Trie la liste des projets étudiés par coût total croissant.
     * <p><b>(TP8 — utilisation de Comparator et sort)</b></p>
     */
    public void trierProjetsParCoutTotalCroissant() {
        projetsEtudies.sort(COMPARATEUR_COUT_TOTAL_ASC);
    }

    /**
     * Retourne le projet le plus "rentable" selon le critère :
     * bénéfice - coût total.
     *
     * @return le projet le plus rentable
     * @throws IllegalStateException si aucun projet n'a encore été étudié
     */
    public Projet getProjetPlusRentable() {
        if (projetsEtudies.isEmpty()) {
            throw new IllegalStateException("Aucun projet étudié — impossible de déterminer le projet le plus rentable.");
        }

        return Collections.max(projetsEtudies, (p1, p2) -> {
            int score1 = p1.getBenefice() - p1.coutTotal();
            int score2 = p2.getBenefice() - p2.coutTotal();
            return Integer.compare(score1, score2);
        });
    }
}
