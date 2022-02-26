package main.java.domain.game;


import main.java.domain.cards.*;
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
        /*
        informationJeu();
         */
        System.out.println("Distribution des cartes !");
        dealCartes();

    }

    public void lancerUneManche(){
        Scanner input = new Scanner(System.in);
        System.out.println("La manche commence !");
        /* TODO CONTINUER LA BOUCLE */


        while(true){
            System.out.println("La carte au sommet du talon est : " + talon.getSommetTalon());
            System.out.println("C'est au joueur : " + players[indiceCurrentPlayer].getNom());
            System.out.println("Sa main est : " + players[indiceCurrentPlayer].getMainJoueur().toString());

            int idx_carte;
            CarteAbstrait carte_choisi;
            boolean aPioche = false;
            boolean aPasse = false;

            do {
                /*
                gerer la pioche autrement, la solution n'est surement pas de faire un do while directement
                peut etre un while !ajouer ou jouer = jouer ou piocher + jouer ou piocher
                 */
                System.out.println("Voulez vous piocher ? Vous pourrez rejouer votre carte directement (boolean)");
                aPioche = input.nextBoolean();

                if(aPioche){
                    players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                }

                System.out.println("Votre main est : " + players[indiceCurrentPlayer].getMainJoueur().toString());
                System.out.println("Voulez vous passer ? (boolean)");
                aPasse = input.nextBoolean();

                if(aPasse){
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
                }

                System.out.println("Choisir une carte compatible ou alors piochez une carte : ");
                idx_carte = Clavier.lireEntier(0, players[indiceCurrentPlayer].nbCartesMain());
                /*
                players[indiceCurrentPlayer].retirerCarte(idx_carte);
                 */
                carte_choisi = players[indiceCurrentPlayer].getMainJoueur().get(idx_carte);
            }while(!carte_choisi.isCompatible(talon.getSommetTalon()) || aPasse);

            //on suppr la carte utilisé de la main et on l'empile sur le talon
            talon.empiler(players[indiceCurrentPlayer].retirerCarte(idx_carte));

            switch(carte_choisi.getType()){
                case SKIP -> {
                    System.out.println("Le prochain joueur est skip!");
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, true);
                }
                case REVERSE -> {
                    System.out.println("Changement de sens!");
                    sensmanche = SensJeu.ANTI_HORAIRE;
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
                }
                case PLUS_DEUX -> {
                    for (int i = 0; i <2; i++) {
                        players[incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false)].getMainJoueur().add(prendreUneCarte());
                    }
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
                }
                case PLUS_QUATRE -> {
                    //choix couleur avant, casting carte???
                    for (int i = 0; i < 4; i++) {
                        players[incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false)].getMainJoueur().add(prendreUneCarte());
                    }
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);

                }
                case CHANG_COULEUR -> {
                    //choix couleur avant, casting carte???
                }
                case NOMBRE -> {
                    indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
                }
            }
            informationJeu();
            System.exit(0);
        }
    }

    public int incrIndiceCurrentPlayer(int currentIndex, SensJeu sens, boolean skip) {
        int finalIndex = currentIndex;

        switch (sens) {
            case HORAIRE -> {
                /*
                ex :
                player.length = 5 (5joueurs 0.1.2.3.4)
                currentindex = 3
                skip donc on doit faire jouer le joueur à l'indice 0
                */
                if (skip) {
                    finalIndex += 2;
                } else {
                    finalIndex += 1;
                }
                if (finalIndex >= players.length) {
                    finalIndex = (finalIndex % players.length);
                }
            }
            case ANTI_HORAIRE -> {
                /*
                ex :
                player.length = 5 (5joueurs 0.1.2.3.4)
                currentindex = 1
                finalindex = -1
                skip donc on doit faire jouer le joueur à l'indice 4
                on fait 5-(-1*-1)=4
                */
                if (skip) {
                    finalIndex -= 2;
                } else {
                    finalIndex -= 1;
                }
                if (finalIndex <= 0) {
                    finalIndex = players.length - (currentIndex * -1);
                }
            }
        }
        return finalIndex;
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
        return pioche.depiler();
    }



}
