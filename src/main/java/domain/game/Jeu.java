package main.java.domain.game;

import main.java.domain.interactions.Interaction;
import main.java.domain.player.Player;

import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

public class Jeu {
    public int nbJoueurs;
    public Player[] players;
    final static int MAXPOINT = 20;

    public Jeu(){
        System.out.println("Nombre de joueurs [2-10] : ");
        nbJoueurs = Interaction.readIntBetweenInterval(2,10);
        initialisationJoueurs(nbJoueurs);
    }

    public void lancer(){
        while(!jeuTermine()){
            Manche manche = new Manche(players);
            manche.lancerUneManche();
        }
        afficherScore();
    }

    //TODO : gerer le cas de l'egalité
    public boolean jeuTermine(){
        for (Player player: players
             ) {
            if(player.getScore() > MAXPOINT){
                return true;
            }
        }
        return false;
    }

    //TODO : gerer le cas de l'egalité
//    public Player getWinner(){
//        for (Player player:players
//        ) {
//            System.out.println("Score du joueur : "+player.getNom() + " = " + player.getScore());
//        }
//    }

    public void afficherScore(){
        for (Player player:players
             ) {
            System.out.println("Score du joueur : "+player.getNom() + " = " + player.getScore());
        }
    }


    public void initialisationJoueurs(int i){
        players = new Player[i];
        String nom;
        for (int j = 0; j < i; j++) {
            System.out.println("Nom du joueur ?");
            nom = Interaction.readString();
            players[j] = new Player(nom, 0);
        }
    }
}
