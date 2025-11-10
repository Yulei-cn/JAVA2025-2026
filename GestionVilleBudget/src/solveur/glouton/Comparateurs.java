package solveur.glouton;

import java.util.Comparator;
import sacADos.Objet;

public class Comparateurs {

    /** Critère f(oi) = utilité / somme des coûts */
    public static Comparator<Objet> f_somme() {
        return (o1, o2) -> {
            double r1 = (double) o1.getUtilite() / somme(o1.getCouts());
            double r2 = (double) o2.getUtilite() / somme(o2.getCouts());
            return Double.compare(r2, r1); // tri décroissant
        };
    }

    /** Critère fmax(oi) = utilité / coût max */
    public static Comparator<Objet> f_max() {
        return (o1, o2) -> {
            double r1 = (double) o1.getUtilite() / max(o1.getCouts());
            double r2 = (double) o2.getUtilite() / max(o2.getCouts());
            return Double.compare(r2, r1);
        };
    }

    private static int somme(int[] t) {
        int s = 0;
        for (int v : t) s += v;
        return s;
    }

    private static int max(int[] t) {
        int max = t[0];
        for (int v : t) if (v > max) max = v;
        return max;
    }
}
