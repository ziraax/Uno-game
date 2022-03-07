package main.java.domain.exec;
import main.java.domain.game.Jeu;


public class main {

    public static void main(String[] args) {
        welcome();
        Jeu jeu = new Jeu();
        jeu.lancer();
        goodbye();
    }

    public static void welcome(){
        System.out.println("Bienvenue dans : ");
        System.out.print("   __  __                                        \n" +
                "  / / / /___  ____     ____ _____ _____ ___  ___ \n" +
                " / / / / __ \\/ __ \\   / __ `/ __ `/ __ `__ \\/ _ \\\n" +
                "/ /_/ / / / / /_/ /  / /_/ / /_/ / / / / / /  __/\n" +
                "\\____/_/ /_/\\____/   \\__, /\\__,_/_/ /_/ /_/\\___/ \n" +
                "                    /____/                       \n" +
                "\n");
    }

    public static void goodbye(){
        System.out.println("\n");
        System.out.println("                              __    __                 \n" +
                "   ____ _  ____   ____   ____/ /   / /_    __  __  ___ \n" +
                "  / __ `/ / __ \\ / __ \\ / __  /   / __ \\  / / / / / _ \\\n" +
                " / /_/ / / /_/ // /_/ // /_/ /   / /_/ / / /_/ / /  __/\n" +
                " \\__, /  \\____/ \\____/ \\__,_/   /_.___/  \\__, /  \\___/ \n" +
                "/____/                                  /____/         ");

        System.out.println("\n");
        System.out.println("Projet realisé par Hugo Walter dans le cadre du cours d'OOP de L2 de l'IDMC");
    }
}


