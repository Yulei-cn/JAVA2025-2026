package solveur.glouton;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sacADos.*;

/**
 * Tests unitaires pour {@link GloutonAjoutSolver}.
 * 
 * Chaque test suit la structure AAA (Arrange, Act, Assert).
 *
 * @author ZHU YULEI
 */
public class GloutonAjoutSolverTest {

    private GloutonAjoutSolver solver;

    private Objet o1, o2, o3;
    private SacADos sac;

    @BeforeEach
    public void init() {
        solver = new GloutonAjoutSolver();

        o1 = new Objet("A", 10, new int[]{4, 3});
        o2 = new Objet("B", 8,  new int[]{3, 3});
        o3 = new Objet("C", 5,  new int[]{10, 10}); // trop cher → jamais sélectionné

        sac = new SacADos(
                2,
                new int[]{7, 7},
                Arrays.asList(o1, o2, o3)
        );
    }

    // ------------------------------------------------------------
    // TEST 1 — Sélection normale
    // ------------------------------------------------------------

    @Test
    public void resoudre_TroisObjets_UnInadmissible_SelectionneDeux() {

        // Act
        List<Objet> sol = solver.resoudre(sac, Comparateurs.f_somme());

        // Assert
        assertEquals(2, sol.size());
        assertTrue(sol.contains(o1));
        assertTrue(sol.contains(o2));
        assertFalse(sol.contains(o3));
    }

    // ------------------------------------------------------------
    // TEST 2 — Aucun objet admissible
    // ------------------------------------------------------------

    @Test
    public void resoudre_TousObjetsTropChers_RetourneVide() {

        SacADos sac2 = new SacADos(
                2,
                new int[]{1, 1},
                Arrays.asList(o1, o2, o3)
        );

        // Act
        List<Objet> sol = solver.resoudre(sac2, Comparateurs.f_somme());

        // Assert
        assertTrue(sol.isEmpty());
    }

    // ------------------------------------------------------------
    // TEST 3 — Ordre dépendant du comparateur
    // ------------------------------------------------------------

    @Test
    public void resoudre_ComparateurMax_SelectionDansOrdreCorrect() {

        // Act
        List<Objet> sol = solver.resoudre(sac, Comparateurs.f_max());

        // Assert
        assertEquals(o1, sol.get(0));  // o1 a le meilleur ratio utilité / max(cout)
    }
}
