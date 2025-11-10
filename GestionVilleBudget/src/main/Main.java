package main;

import java.util.Arrays;
import java.util.List;

import sacADos.*;
import solveur.glouton.*;

public class Main {
    public static void main(String[] args) {

        Objet o1 = new Objet(10, new int[]{4, 3, 2});
        Objet o2 = new Objet(5, new int[]{1, 10, 4});
        Objet o3 = new Objet(8, new int[]{3, 3, 3});

        SacADos sac = new SacADos(
                3,
                new int[]{6, 6, 6},
                Arrays.asList(o1, o2, o3)
        );

        GloutonAjoutSolver solver = new GloutonAjoutSolver();

        List<Objet> solution = solver.resoudre(sac, Comparateurs.f_somme());

        System.out.println(solution);
    }
}
