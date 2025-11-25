package TP9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Personne {

    private static int compteurID = 0; // 自动生成唯一ID

    private String nom;
    private int id;

    private List<Integer> objetsObtenus = new ArrayList<>();
    private List<Integer> preferences = new ArrayList<>();


    // ============================
    // Constructeurs
    // ============================

    public Personne() {
        this.nom = "Anonyme";
        this.id = compteurID++;
    }

    public Personne(String nom, int nbObjets) {
        this.nom = nom;
        this.id = compteurID++;

        // 生成 0..nbObjets-1 的随机排列
        for (int i = 0; i < nbObjets; i++)
            preferences.add(i);

        Collections.shuffle(preferences);
    }


    // ============================
    // Méthodes demandées
    // ============================

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", id=" + id +
                ", objetsObtenus=" + objetsObtenus +
                ", preferences=" + preferences +
                '}';
    }


    public void addItem(int i) {
        objetsObtenus.add(i);
    }


    /**
     * Compare les préférences :
     * @return  1 si oi est préféré à oj
     *         -1 si oj est préféré à oi
     *          0 si i == j
     */
    public int preferences(int i, int j) {

        int rang_i = preferences.indexOf(i);
        int rang_j = preferences.indexOf(j);

        if (rang_i < rang_j) return 1;   // i 更受喜欢
        if (rang_i > rang_j) return -1;  // j 更受喜欢
        return 0;
    }


    // Getters utiles
    public List<Integer> getPreferences() { return preferences; }
    public List<Integer> getObjetsObtenus() { return objetsObtenus; }
    public String getNom() { return nom; }
    public int getId() { return id; }
}
