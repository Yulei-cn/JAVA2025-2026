package solveur.hillclimbing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sacADos.*;

/**
 * Tests unitaires pour {@link HillClimbingSolver}.
 *
 * Respecte la structure AAA et les conventions de nommage recommandées.
 * Teste trois aspects :
 *  - amélioration ou stabilité de la solution (jamais pire)
 *  - optimal local atteint correctement
 *  - solution admissible
 *
 * Auteur : ZHU YULEI
 */
public class HillClimbingSolverTest {

    private HillClimbingSolver solver;
    private SacADos sac;
    private Objet o1, o2, o3;

    @BeforeEach
    public void init() {

        solver = new HillClimbingSolver();

        o1 = new Objet("A", 10, new int[]{4});
        o2 = new Objet("B", 9,  new int[]{4});
        o3 = new Objet("C", 2,  new int[]{1}); // très faible utilité

        sac = new SacADos(
                1,
                new int[]{8},  // budget = 8
                Arrays.asList(o1, o2, o3)
        );
    }

    // ------------------------------------------------------------
    // TEST 1 — Une mauvaise solution doit être améliorée ou maintenue
    // ------------------------------------------------------------

    @Test
    public void resoudre_SolutionMauvaise_NeverWorse() {

        // Arrange
        List<Objet> mauvaise = List.of(o3);  // utilité très faible

        // Act
        List<Objet> sol = solver.resoudre(sac, mauvaise);

        // Assert
        assertTrue(
                sac.utiliteTotale(sol) >= sac.utiliteTotale(mauvaise),
                "Hill Climbing ne doit jamais produire une solution pire"
        );
    }

    // ------------------------------------------------------------
    // TEST 2 — Vérifie que le meilleur voisin est trouvé correctement
    // ------------------------------------------------------------

    @Test
    public void resoudre_OptimumLocalTrouve() {

        // Arrange : solution initiale très mauvaise
        List<Objet> initiale = List.of(o3);

        // Act
        List<Objet> sol = solver.resoudre(sac, initiale);

        // Assert : optimum local = o1 + o2 (utilité = 19)
        int utilite = sac.utiliteTotale(sol);

        assertEquals(19, utilite, 
            "Hill Climbing doit atteindre l'optimum local (o1 + o2)");
    }

    // ------------------------------------------------------------
    // TEST 3 — Vérifie que la solution finale est admissible
    // ------------------------------------------------------------

    @Test
    public void resoudre_SolutionAdmissibleToujours() {

        // Arrange
        List<Objet> initiale = List.of(o3);

        // Act
        List<Objet> sol = solver.resoudre(sac, initiale);

        int coutTotal = sol.stream().mapToInt(o -> o.getCouts()[0]).sum();

        // Assert
        assertTrue(
                coutTotal <= sac.getBudgets()[0],
                "La solution Hill Climbing doit toujours être admissible"
        );
    }
}
