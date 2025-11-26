package TP8.biblio;

public abstract class Livre {

    protected String auteur;
    protected String titre;
    protected int anneePub;

    public Livre(String auteur, String titre, int anneePub) {
        this.auteur = auteur;
        this.titre = titre;
        this.anneePub = anneePub;
    }

    public abstract void presentation();

    public String toString() {
        return titre + " (" + anneePub + "), auteur : " + auteur;
    }

    public int getAnnee() {
        return this.anneePub;
    }
}
