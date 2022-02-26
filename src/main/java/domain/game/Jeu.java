package main.java.domain.game;

import main.java.domain.interactions.Clavier;
import main.java.domain.player.Player;

import java.util.Objects;

public class Jeu {
    public int nbJoueurs;
    public Player[] players;
    final static int MAXPOINT = 500;

    public Jeu(){
        System.out.println("nbjoueur");
        nbJoueurs = Clavier.lireEntier(2,10);

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
            nom = Clavier.lireChaine();
            players[j] = new Player(nom, 0);
        }
    }


    /**
     *
     * @param pseudoJoueur a verifier
     * @param indice du joueur dans le tableau de joueur
     * @return un booleen qui dit si le pseudo est valide (<=> unique)
     */
    private boolean boolValidePseudo(String pseudoJoueur) {

        for (Player player: players
        ) {
            String nom;
            boolean isNomUnique;

            if(Objects.equals(player.getNom(), pseudoJoueur)){
                return false;
            }
        }
        return true;


        /*
        if (indice == 0) {
            return true;
        }
        for (int j = 0; j < indice; j++) {
            if (players[j].getNom().equalsIgnoreCase(pseudoJoueur)) {
                return false;
            }
        }
        return true;


         */
    }
}
