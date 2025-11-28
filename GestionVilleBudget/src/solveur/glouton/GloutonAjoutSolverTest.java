package solveur.glouton;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sacADos.*;

public class GloutonAjoutSolverTest {

    @Test
    public void testGloutonAjout() {

        Objet o1 = new Objet(10, new int[]{4, 3});
        Objet o2 = new Objet(8,  new int[]{3, 3});
        Objet o3 = new Objet(5,  new int[]{10, 10}); // trop cher

        SacADos sac = new SacADos(
            2,
            new int[]{7, 7},
            Arrays.asList(o1, o2, o3)
        );

        GloutonAjoutSolver solver = new GloutonAjoutSolver();
        List<Objet> sol = solver.resoudre(sac, Comparateurs.f_somme());

        assertEquals(2, sol.size());
        assertTrue(sol.contains(o1));
        assertTrue(sol.contains(o2));
    }
}
