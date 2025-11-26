package equipe;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Classe de test pour valider le fonctionnement de l'équipe municipale.
 */
public class TestEquipe {

    public static void main(String[] args) {

        System.out.println("=== Test du système de gestion des budgets de la ville ===\n");

        // 1. Création de l'élu
        Elu elu = new Elu("Martin", "Pierre", 45);

        // 2. Création des évaluateurs (un par type de coût)
        List<Evaluateur> evaluateurs = new ArrayList<>();
        evaluateurs.add(new Evaluateur("Dupont", "Marie", 35, TypeCout.ECONOMIQUE));
        evaluateurs.add(new Evaluateur("Durant", "Sophie", 40, TypeCout.SOCIAL));
        evaluateurs.add(new Evaluateur("Bernard", "Luc", 38, TypeCout.ENVIRONNEMENTAL));

        // 3. Création des experts
        List<Expert> experts = new ArrayList<>();
        experts.add(new Expert("Leroy", "Jean", 42,
                EnumSet.of(Secteur.SPORT, Secteur.EDUCATION)));
        experts.add(new Expert("Moreau", "Claire", 39,
                EnumSet.of(Secteur.SANTE, Secteur.CULTURE)));
        experts.add(new Expert("Simon", "Paul", 44,
                EnumSet.of(Secteur.ATTRACTIVITE_ECONOMIQUE)));

        // 4. Construction de l'équipe municipale
        EquipeMunicipale equipe = new EquipeMunicipale(elu, evaluateurs, experts);

        // 5. Affichage de l'équipe
        equipe.afficherEquipe();

        // 6. Exécution d'un premier cycle de simulation
        equipe.executerCycleSimulation(2);

        // 7. Affichage des projets évalués
        equipe.afficherProjets();

        // 8. Deuxième cycle
        System.out.println("\n=== Second cycle de simulation ===\n");
        equipe.executerCycleSimulation(1);
        equipe.afficherProjets();

        System.out.println("\n=== Test terminé ===");
    }
}
