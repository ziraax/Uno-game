package main.java.domain.exec;
import main.java.domain.game.Jeu;


public class main {

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        jeu.lancer();
        jeu.afficherScore();
    }
}
