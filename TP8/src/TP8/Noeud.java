package TP8;

import java.util.*;

public class Noeud<T> {

    private T val;
    private List<Noeud<T>> voisins = new LinkedList<>();
    boolean visite = false;

    public Noeud() {}

    public Noeud(T val) {
        this.val = val;
    }

    public T getValue() {
        return val;
    }

    public void ajoutVoisin(Noeud<T> n) {
        voisins.add(n);
    }

    public List<Noeud<T>> getVoisins() {
        return voisins;
    }
}
