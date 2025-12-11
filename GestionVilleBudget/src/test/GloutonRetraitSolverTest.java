package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sacADos.*;
import solveur.glouton.Comparateurs;
import solveur.glouton.GloutonRetraitSolver;

/**
 * Tests unitaires pour {@link GloutonRetraitSolver}.
 *
 * Chaque test suit le modèle AAA : Arrange, Act, Assert.
 *
 * @author ZHU YULEI
 */
public class GloutonRetraitSolverTest {

    private GloutonRetraitSolver solver;

    private Objet o1, o2, o3;
    private SacADos sac;

    @BeforeEach
    public void init() {
        solver = new GloutonRetraitSolver();

        o1 = new Objet("A", 10, new int[]{6});
        o2 = new Objet("B", 9,  new int[]{6});
        o3 = new Objet("C", 1,  new int[]{20}); // trop cher → provoque retrait

        sac = new SacADos(
                1,
                new int[]{10},
                Arrays.asList(o1, o2, o3)
        );
    }

    // ------------------------------------------------------------
    // TEST 1 — Retrait d’un objet trop cher, ajout impossible
    // ------------------------------------------------------------

    @Test
    public void resoudre_RetraitObjetTropCher_GardeUnSeulObjet() {

        // Act
        List<Objet> sol = solver.resoudre(
                sac,
                Comparateurs.f_somme(),
                Comparateurs.f_max()
        );

        // Assert
        assertEquals(1, sol.size());
        assertTrue(sol.contains(o1) || sol.contains(o2));
        assertFalse(sol.contains(o3));
    }

    // ------------------------------------------------------------
    // TEST 2 — Tous objets admissibles, aucun retrait
    // ------------------------------------------------------------

    @Test
    public void resoudre_AucunRetrait_TousObjetsConserves() {

        SacADos sac2 = new SacADos(
                1,
                new int[]{20},
                Arrays.asList(o1, o2)
        );

        // Act
        List<Objet> sol = solver.resoudre(
                sac2,
                Comparateurs.f_somme(),
                Comparateurs.f_max()
        );

        // Assert
        assertEquals(2, sol.size());
        assertTrue(sol.contains(o1));
        assertTrue(sol.contains(o2));
    }

    // ------------------------------------------------------------
    // TEST 3 — Vérifier que le solveur retourne une solution admissible
    // ------------------------------------------------------------

    @Test
    public void resoudre_SolutionToujoursAdmissible() {

        // Act
        List<Objet> sol = solver.resoudre(
                sac,
                Comparateurs.f_somme(),
                Comparateurs.f_max()
        );

        // Assert : somme des couts ≤ budget !
        int total = sol.stream().mapToInt(o -> o.getCouts()[0]).sum();

        assertTrue(total <= sac.getBudgets()[0]);
    }
}
