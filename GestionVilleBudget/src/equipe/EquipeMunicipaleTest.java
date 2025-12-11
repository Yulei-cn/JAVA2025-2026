package equipe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe {@link EquipeMunicipale}.
 *
 * Chaque test suit la méthode AAA :
 *  - Arrange
 *  - Act
 *  - Assert
 *
 * @author ZHU YULEI
 */
public class EquipeMunicipaleTest {

    private EquipeMunicipale equipe;

    @BeforeEach
    public void init() {

        Elu elu = new Elu("Martin", "Pierre", 45);

        List<Evaluateur> evaluateurs = List.of(
                new Evaluateur("Dupont", "Marie", 35, TypeCout.ECONOMIQUE),
                new Evaluateur("Durant", "Sophie", 40, TypeCout.SOCIAL),
                new Evaluateur("Bernard", "Luc", 38, TypeCout.ENVIRONNEMENTAL)
        );

        List<Expert> experts = List.of(
                new Expert("Leroy", "Jean", 42, EnumSet.of(Secteur.SPORT, Secteur.EDUCATION)),
                new Expert("Moreau", "Claire", 39, EnumSet.of(Secteur.SANTE, Secteur.CULTURE)),
                new Expert("Simon", "Paul", 44, EnumSet.of(Secteur.ATTRACTIVITE_ECONOMIQUE))
        );

        equipe = new EquipeMunicipale(elu, evaluateurs, experts);
    }

    // ------------------------------------------------------------
    // TEST 1
    // ------------------------------------------------------------

    @Test
    public void executerCycleSimulation_AvecNbProjets_ProjetsBienCrees() {

        // Act
        equipe.executerCycleSimulation(2);

        // Assert
        assertEquals(3 * 2, equipe.getProjetsEtudies().size(),
                "Chaque expert doit produire nbProjets projets.");
    }

    // ------------------------------------------------------------
    // TEST 2
    // ------------------------------------------------------------

    @Test
    public void executerCycleSimulation_ProjetsOntBeneficeEtCoutsValides() {

        equipe.executerCycleSimulation(1);

        var projets = equipe.getProjetsEtudies();

        assertFalse(projets.isEmpty());

        projets.forEach(p -> {
            assertTrue(p.getBenefice() > 0);
            assertTrue(p.getCoutEconomique() >= 0);
            assertTrue(p.getCoutSocial() >= 0);
            assertTrue(p.getCoutEnvironnemental() >= 0);
        });
    }

    // ------------------------------------------------------------
    // TEST 3
    // ------------------------------------------------------------

    @Test
    public void getProjetsEtudies_ApresPlusieursCycles_TailleCorrecte() {

        equipe.executerCycleSimulation(1);
        equipe.executerCycleSimulation(2);

        assertEquals(3 * 3, equipe.getProjetsEtudies().size());
    }
}
