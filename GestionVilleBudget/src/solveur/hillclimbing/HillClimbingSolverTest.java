package solveur.hillclimbing;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sacADos.*;

public class HillClimbingSolverTest {

    @Test
    public void testHillClimbingImproves() {

        Objet o1 = new Objet(10, new int[]{4});
        Objet o2 = new Objet(9,  new int[]{4});
        Objet o3 = new Objet(2,  new int[]{1}); // très faible utilité

        SacADos sac = new SacADos(
            1,
            new int[]{8},
            Arrays.asList(o1, o2, o3)
        );

        List<Objet> mauvaise = List.of(o3); // très mauvaise solution

        HillClimbingSolver solver = new HillClimbingSolver();
        List<Objet> sol = solver.resoudre(sac, mauvaise);

        assertTrue(sac.utiliteTotale(sol) >= sac.utiliteTotale(mauvaise));
    }
}
