package main.java.domain.game;

import main.java.domain.interactions.Interaction;
import main.java.domain.player.Player;

public class Jeu {
    private final int nbJoueurs;
    protected Player[] players;
    final static int MAXPOINT = 500;

    public Jeu(){
        System.out.println("Nombre de joueurs [2-10] : ");
        nbJoueurs = Interaction.readIntBetweenInterval(2,10);
        initialisationJoueurs();
    }

    public void lancer(){
        while(!jeuTermine()){
            Manche manche = new Manche(players);
            manche.lancerUneManche();
        }

        System.out.println("================FIN DE LA PARTIE================");
        getAndPrintWinners();
        afficherScore();
    }

    private boolean jeuTermine(){
        for (Player player: players
             ) {
            if(player.getScore() > MAXPOINT){
                return true;
            }
        }
        return false;
    }

    private void getAndPrintWinners(){
        int min = players[0].getScore();

        for (Player player: players) {
            if(player.getScore() < min) {
                min = player.getScore();
            }
        }

        for(Player player: players) {
            if(player.getScore() == min) {
                System.out.println("Nous avons un gagnant : " + player.getNom() + " avec : " + player.getScore() + " point(s).");
            }
        }

    }

    public void afficherScore(){
        for (Player player:players
             ) {
            System.out.println("Score du joueur : "+player.getNom() + " = " + player.getScore());
        }
    }

    private void initialisationJoueurs(){
        players = new Player[nbJoueurs];
        String nom;
        for (int j = 0; j < nbJoueurs; j++) {
            System.out.println("Nom du joueur ?");
            nom = Interaction.readString();
            players[j] = new Player(nom, 0);
        }
    }
}
