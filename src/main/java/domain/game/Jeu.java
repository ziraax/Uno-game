package main.java.domain.game;

import main.java.domain.interactions.Interaction;
import main.java.domain.player.Player;

import java.util.Objects;

public class Jeu {
    public int nbJoueurs;
    public Player[] players;
    final static int MAXPOINT = 500;

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

    public boolean jeuTermine(){
        for (Player player: players
             ) {
            if(player.getScore() > MAXPOINT){
                return true;
            }
        }
        return false;
    }

    public void afficherScore(){
        for (Player player:players
             ) {
            System.out.println("Score du joueur : "+player.getScore() + " = " + player.getScore());
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
