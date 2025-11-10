package tp7;

import java.util.Scanner;  // 为 console 输入准备

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choix;

        do {
            afficherMenu();
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine(); // 清理换行符

            switch (choix) {
                case 0 -> System.out.println("👉 退出菜单。");
                case 1 -> ecrireDansFichier();
                case 2 -> lireFichier();
                case 3 -> ecrireBox();
                case 4 -> lireBox();
                default -> System.out.println("⚠️ 请输入 0 - 4 之间的选项。");
            }

        } while (choix != 0);
    }

    public static void afficherMenu() {
        System.out.println("------------ 菜单 -------------");
        System.out.println("输入 0：退出");
        System.out.println("输入 1：写入文本到文件");
        System.out.println("输入 2：读取文件内容");
        System.out.println("输入 3：把 Box 对象保存到文件");
        System.out.println("输入 4：从文件读取 Box 对象");
        System.out.println("--------------------------------");
    }
    
    public static void ecrireDansFichier() {
        try {
            System.out.print("请输入文件名：");
            String nom = sc.nextLine();

            System.out.print("请输入要写入的句子：");
            String phrase = sc.nextLine();

            java.io.FileWriter fw = new java.io.FileWriter(nom);
            fw.write(phrase);
            fw.close();

            System.out.println("✅ 写入完成！");
        } catch (Exception e) {
            System.out.println("⚠️ 写入文件时出错：" + e.getMessage());
        }
    }
    
    public static void lireFichier() {
        try {
            System.out.print("请输入文件名：");
            String nom = sc.nextLine();

            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader(nom)
            );

            String ligne;
            System.out.println("----- 文件内容 ↓ -----");
            while ((ligne = br.readLine()) != null) {
                System.out.println(ligne);
            }
            br.close();
            System.out.println("-----------------------");

        } catch (Exception e) {
            System.out.println("⚠️ 读取文件时出错：" + e.getMessage());
        }
    }
    
    public static void ecrireBox() {
        try {
            System.out.print("请输入保存的文件名：");
            String nom = sc.nextLine();

            System.out.print("请输入 Box 的 x：");
            int x = sc.nextInt();
            System.out.print("请输入 Box 的 y：");
            int y = sc.nextInt();
            System.out.print("请输入 Box 的 z：");
            int z = sc.nextInt();
            sc.nextLine(); // 吃掉换行符

            Box box = new Box(x, y, z);

            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(nom)
            );
            oos.writeObject(box);
            oos.close();

            System.out.println("✅ Box 已保存：" + box);

        } catch (Exception e) {
            System.out.println("⚠️ 保存 Box 时出错：" + e.getMessage());
        }
    }

    
    public static void lireBox() {
        try {
            System.out.print("请输入文件名：");
            String nom = sc.nextLine();

            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(nom)
            );

            Box box = (Box) ois.readObject();
            ois.close();

            System.out.println("✅ 读取到 Box：" + box);

        } catch (Exception e) {
            System.out.println("⚠️ 读取 Box 时出错：" + e.getMessage());
        }
    }



}


