package TP8;

import java.util.List;
import java.util.LinkedList;

public class TestGraphe {

    // ========== Q4 unionVal ==========
    public static <E> List<E> unionVal(Graphe<? extends E> g1, Graphe<? extends E> g2) {

        List<E> l = new LinkedList<>();

        for (Noeud<? extends E> n : g1.getNoeuds())
            l.add(n.getValue());

        for (Noeud<? extends E> n : g2.getNoeuds())
            l.add(n.getValue());

        return l;
    }

    // ========== main ==========
    public static void main(String[] args) {

        Graphe<String> g1 = new Graphe<>();
        g1.addNoeud("A");
        g1.addNoeud("B");

        Graphe<Integer> g2 = new Graphe<>();
        g2.addNoeud(10);
        g2.addNoeud(20);

        List<Object> res = unionVal(g1, g2);
        System.out.println(res);
    }
}
