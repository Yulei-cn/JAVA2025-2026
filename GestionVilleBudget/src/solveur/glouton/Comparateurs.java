package solveur.glouton;

import java.util.Comparator;
import java.util.List;
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
    
    /** Critère fmv : utilité / coût dans la dimension la plus en dépassement */
    public static Comparator<Objet> fmv(int[] budgets, List<Objet> selection) {
        return (o1, o2) -> {
            double score1 = scoreFMV(o1, budgets, selection);
            double score2 = scoreFMV(o2, budgets, selection);
            return Double.compare(score2, score1); // décroissant
        };
    }

    private static double scoreFMV(Objet o, int[] budgets, List<Objet> selection) {
        int dimension = budgets.length;
        int[] consommation = new int[dimension];

        for (Objet obj : selection)
            for (int i = 0; i < dimension; i++)
                consommation[i] += obj.getCouts()[i];

        // retrouver l'indice de la dimension la plus en dépassement
        int worstDim = 0;
        int maxDepassement = Integer.MIN_VALUE;
        for (int i = 0; i < dimension; i++) {
            int diff = consommation[i] - budgets[i];
            if (diff > maxDepassement) {
                maxDepassement = diff;
                worstDim = i;
            }
        }

        return (double) o.getUtilite() / o.getCouts()[worstDim];
    }

}

