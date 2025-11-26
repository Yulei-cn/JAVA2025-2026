package TP8.biblio;

public enum Registre {
    FANTASTIQUE(0),
    DRAMATIQUE(0),
    ROMANCE(0);

    private int nb;

    Registre(int nb) {
        this.nb = nb;
    }

    public void incrementer() {
        nb++;
    }

    public int getNb() {
        return nb;
    }

    public static void AfficherNbParRegistre() {
        for (Registre r : Registre.values()) {
            System.out.println(r + " : " + r.nb + " romans");
        }
    }
}
