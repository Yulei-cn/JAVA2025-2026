package solveur.glouton;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sacADos.*;

public class GloutonRetraitSolverTest {

    @Test
    public void testGloutonRetrait() {

        Objet o1 = new Objet(10, new int[]{6});
        Objet o2 = new Objet(9,  new int[]{6});
        Objet o3 = new Objet(1,  new int[]{20}); // forcera retrait

        SacADos sac = new SacADos(
            1,
            new int[]{10},
            Arrays.asList(o1, o2, o3)
        );

        GloutonRetraitSolver solver = new GloutonRetraitSolver();
        List<Objet> sol = solver.resoudre(
            sac,
            Comparateurs.f_somme(),
            Comparateurs.f_max()
        );

        assertEquals(1, sol.size());
        assertTrue(sol.contains(o1) || sol.contains(o2));
    }
}
