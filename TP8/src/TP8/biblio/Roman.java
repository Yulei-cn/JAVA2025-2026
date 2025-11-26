package TP8.biblio;

public class Roman extends Livre {

    private Registre registre;

    public Roman(String auteur, Registre registre, String titre, int anneePub) {
        super(auteur, titre, anneePub);
        this.registre = registre;
        this.registre.incrementer();  // Q1 要求：计数++
    }

    @Override
    public void presentation() {
        System.out.println("Roman : " + titre + " (" + anneePub + ")");
        System.out.println("Auteur : " + auteur);
        System.out.println("Registre : " + registre);
    }

    @Override
    public String toString() {
        return "Roman : " + super.toString() + ", registre : " + registre;
    }
}
