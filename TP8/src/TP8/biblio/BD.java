package TP8.biblio;

public class BD extends Livre {

    private String dessinateur;

    public BD(String auteur, String dessinateur, String titre, int anneePub) {
        super(auteur, titre, anneePub);
        this.dessinateur = dessinateur;
    }

    @Override
    public void presentation() {
        System.out.println("BD : " + titre + " (" + anneePub + ")");
        System.out.println("Auteur : " + auteur);
        System.out.println("Dessinateur : " + dessinateur);
    }

    @Override
    public String toString() {
        return "BD : " + super.toString() + ", dessinateur : " + dessinateur;
    }
}
