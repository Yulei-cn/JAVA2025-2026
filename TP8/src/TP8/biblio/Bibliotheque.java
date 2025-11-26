package TP8.biblio;

import java.util.*;

public class Bibliotheque {

    private LinkedList<Livre> livres = new LinkedList<>();

    public void ajouter(Livre l) {
        livres.add(l);
    }

    public void afficherBibliotheque() {
        System.out.println("Voici votre bibliothèque\n---------------");
        for (Livre l : livres)
            l.presentation();
    }

    // Q5 — trier par année
    public void trieParDate() {
        livres.sort(new Comparator<Livre>() {
            @Override
            public int compare(Livre o1, Livre o2) {
                return o1.getAnnee() - o2.getAnnee(); // 年份升序
            }
        });
    }

    public LinkedList<Livre> getLivres() {
        return livres;
    }
}
