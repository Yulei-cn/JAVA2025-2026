package TP9;

import java.util.*;


public class Affectation {

    // ============= ATTRIBUTS ==============

    private List<Integer> objets = new ArrayList<>();
    private Personne[] personnes;
    private static Random r = new Random();
    private List<Pair<Personne, Integer>> protocole = new LinkedList<>();


    // ============= CONSTRUCTEUR (Q3) ==============

    public Affectation(int n, int m) {

        // ---- 1. créer la liste d’objets : 0 .. n-1 ----
        for (int i = 0; i < n; i++)
            objets.add(i);

        // ---- 2. créer les personnes ----
        personnes = new Personne[m];
        for (int i = 0; i < m; i++)
            personnes[i] = new Personne("Personne" + i, n); // 每人 n 个对象范围的偏好

        // ---- 3. créer protocole aléatoire ----
        int total = 0;

        while (total < n) {

            // x = 应分配的数量，至少 1
            int x = 1 + r.nextInt(n - total);

            // y = 随机挑一位 person
            int y = r.nextInt(m);

            protocole.add(new Pair<Personne, Integer>(personnes[y], x));

            total += x;
        }
    }


    // ============= GETTERS UTILES ==============

    public List<Integer> getObjets() { return objets; }
    public Personne[] getPersonnes() { return personnes; }
    public List<Pair<Personne, Integer>> getProtocole() { return protocole; }


    // ============= CLASSE INTERNE PAIR (Q4, 下一步完成) ==============
    public static class Pair<F, S> {
        private F first;
        private S second;

        public Pair(F f, S s) {
            first = f;
            second = s;
        }
        public F getFirst() { return first; }
        public S getSecond() { return second; }
    }
    
    public void affectation(Personne p, int nb) {

        // ---- 排序 objets 根据 p 的偏好 ----
        objets.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return p.preferences(o1, o2);  
                // 如果 p 更喜欢 o1，则返回 -1（意味着 o1 排在前）
                // 如果更喜欢 o2，则返回 1
                // 如果同样喜欢或相等则返回 0
            }
        });

        // ---- 分配 nb 个最喜欢的对象 ----
        for (int i = 0; i < nb; i++) {
            p.addItem(objets.remove(0));  // 拿走最喜欢的那一个
        }
    }
    
    public void affectation() {
        for (Pair<Personne, Integer> pair : protocole) {
            Personne p = pair.getFirst();
            int nb = pair.getSecond();

            affectation(p, nb);
        }
    }


}
