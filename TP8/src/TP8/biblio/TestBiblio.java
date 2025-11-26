package TP8.biblio;

public class TestBiblio {

    public static void main(String[] args) {

        Bibliotheque b = new Bibliotheque();

        b.ajouter(new Roman("Helen Fielding", Registre.ROMANCE,
                "Le Journal de Bridget Jones", 1996));

        b.ajouter(new Roman("J.R.R. Tolkien", Registre.FANTASTIQUE,
                "Le Seigneur des anneaux", 1954));

        b.ajouter(new BD("Hergé", "Hergé",
                "Tintin au Congo", 1931));

        b.ajouter(new BD("René Goscinny", "Albert Uderzo",
                "Les Douze Travaux d’Astérix", 1976));

        System.out.println("\n=== Avant tri ===");
        b.afficherBibliotheque();

        System.out.println("\n=== Après tri par date ===");
        b.trieParDate();
        b.afficherBibliotheque();

        System.out.println("\n=== Nombre de romans par registre ===");
        Registre.AfficherNbParRegistre();
    }
}
