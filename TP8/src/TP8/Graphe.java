package TP8;

import java.util.*;

public class Graphe<T> {

    List<Noeud<T>> noeuds = new LinkedList<>();

    public void addNoeud(T val) {
        noeuds.add(new Noeud<T>(val));
    }

    public void ajoutArete(int i, int j) {
        Noeud<T> a = noeuds.get(i);
        Noeud<T> b = noeuds.get(j);

        a.ajoutVoisin(b);
        b.ajoutVoisin(a);
    }

    public List<Noeud<T>> getNoeuds() {
        return noeuds;
    }
    
    public List<T> dfs(int id) {
        for (Noeud<T> n : noeuds) n.visite = false;

        List<T> res = new LinkedList<>();
        dfsRec(noeuds.get(id), res);
        return res;
    }

    private void dfsRec(Noeud<T> n, List<T> res) {
        if (n.visite) return;
        n.visite = true;
        res.add(n.getValue());

        for (Noeud<T> v : n.getVoisins()) {
            dfsRec(v, res);
        }
    }

}
