package main.java.domain.game;


import main.java.domain.cards.CarteAbstrait;
import main.java.domain.cards.CarteAction;
import main.java.domain.interactions.Clavier;
import main.java.domain.player.Player;

import java.util.*;


public class Manche {

    public Pioche pioche = new Pioche();
    public Talon talon = new Talon(pioche);
    private int indiceCurrentPlayer;
    public SensJeu sensmanche;

    public Player[] players;


    public Manche(Player[] players){

        this.players = players;
        Pioche pioche = new Pioche();
        Talon talon = new Talon(pioche);
        sensmanche = SensJeu.HORAIRE;

        Random r = new Random();
        indiceCurrentPlayer = r.nextInt(players.length);

        dealCartes();
        informationJeu();
    }

    public void lancerUneManche(){

        System.out.println("La manche commence !");
        /* TODO CONTINUER LA BOUCLE */



        while(true){

            /*
            System.out.println("Tour de " + currentPlayer.getNom());

            if(talon.getSommetTalon() instanceof CarteAction)

             */
        }


    }

    private void informationJeu(){
        for (Player player: players
        ) {
            System.out.println(Arrays.toString(player.getMainJoueur().toArray()) + player.getNom());
        }
    }

    public void dealCartes(){
        for(int nbCarte = 0; nbCarte < 6; nbCarte++){
            for (Player player: players
            ) {
                player.ajoutCarte(pioche.depiler());
            }
        }
    }


    /**
     *
     * @return la carte tirée de la pioche
     */
    public CarteAbstrait prendreUneCarte(){
        if (pioche.nbCarte() == 0){
            /* ATTENTION IL Y A LE SHUFFLE IL FAUDRA TROUVER UNE SOLUTION */
            System.out.println("Pioche vide!");

            CarteAbstrait sommetTalon = talon.depiler();

            while(talon.nbCarte() != 0){
                pioche.empiler(talon.depiler());
            }
            //pioche.melanger
            talon.empiler(sommetTalon);
        }
        CarteAbstrait carte = pioche.depiler();
        return carte;
    }



}
