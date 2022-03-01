package main.java.domain.game;


import main.java.domain.cards.*;
import main.java.domain.interactions.Clavier;
import main.java.domain.player.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static main.java.domain.interactions.Clavier.*;


public class Manche {

    public Pioche pioche = new Pioche();
    public Talon talon = new Talon(pioche);
    private int indiceCurrentPlayer;
    public SensJeu sensmanche;

    public Player[] players;

    static Scanner input = new Scanner(System.in);


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

        System.out.println("La manche commence !");
        while(!hasSomeoneNoCard()){
            System.out.println("Pour afficher la main du joueur, taper /m");
            System.out.println("Pour piocher une carte, taper /p");
            System.out.println("Pour jouer une carte, taper /j n");
            System.out.println("Pour passer votre tour, taper /e");
            System.out.println("\n");
            System.out.println("La carte au sommet du talon est : " + talon.getSommetTalon());
            applyCardEffect(talon.getSommetTalon());

            System.out.println("C'est au joueur : " + players[indiceCurrentPlayer].getNom());


            //pour passer au prochain tour, il faut soit que le joueur courant ait pris une carte puis passé ou joué, soit qu'il ait joué une carte

            //0 = aPioche
            //1 = aPasse
            //2 = aJoue
            Boolean[] arrayControl = new Boolean[3];
            arrayControl[0] = false;
            arrayControl[1] = false;
            arrayControl[2] = false;

            CarteAbstrait carte_choisi = readCommand(arrayControl);

            //on suppr la carte utilisé de la main et on l'empile sur le talon

            if(!(carte_choisi == null)){
                applyCardEffect(carte_choisi);
            } else {
                indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
            }

            //attention a l'enlever, sachant que sert juste a faire des verifs
            informationJeu();
            /*
            System.exit(0);
             */
        }
        setPointsForAllPlayers();
        afficherScore();
    }

    public boolean hasSomeoneNoCard(){
        for(Player player: players
        ){
            if(player.getMainJoueur().size() == 0){
                return true;
            }
        }
        return false;
    }

    public void setPointsForAllPlayers(){
        for(Player player: players
        ) {
            int score = 0;
            for (int i = 0; i < player.getMainJoueur().size(); i++) {
                if(player.getMainJoueur().get(i) instanceof CarteNombre){
                    score += ((CarteNombre) player.getMainJoueur().get(i)).getNumCard();
                }
                if(player.getMainJoueur().get(i) instanceof CarteAction){
                    score += 20;
                }
                if(player.getMainJoueur().get(i) instanceof CarteJoker){
                    score += 50;
                }
            }
            player.setScore(score);
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

    public CarteAbstrait readCommand(Boolean[] arrayControl){
        while (true){

            int idx_carte;
            CarteAbstrait carte_choisi;

            String userInput = input.nextLine();
            try{
                if(useRegexNormalCard(userInput) && !arrayControl[2]){
                    System.out.println(" " + userInput);

                    do {
                        idx_carte = (userInput.charAt(3)-'0');

                        carte_choisi = players[indiceCurrentPlayer].getMainJoueur().get(idx_carte);

                        /**
                         * TODO : check if joker card, if yes, ask the colour and use method to select colour
                         */
                        if (carte_choisi instanceof CarteJoker){
                            System.out.println("C'est une carte Joker !"+
                                                "Vous devez rentrer /j "+idx_carte+" couleur");


                            ((CarteJoker) carte_choisi).setCouleurCarteJoker(chooseJokerCardColor());

                            break;

                        }

                    }while(!carte_choisi.isCompatible(talon.getSommetTalon()));

                    talon.empiler(players[indiceCurrentPlayer].retirerCarte(idx_carte));
                    System.out.println("Le joueur : " + players[indiceCurrentPlayer].getNom() +
                                        "joue un " + carte_choisi.getType() +
                                        "de couleur " + carte_choisi.getCouleur()
                                        );

                    arrayControl[2] = true;
                    return carte_choisi;
                }
                if(useRegexShowHand(userInput)){
                    System.out.println("La main du joueur "+ players[indiceCurrentPlayer].getNom() + " est " + players[indiceCurrentPlayer].getMainJoueur().toString());
                    continue;
                }
                if(useRegexTakeCard(userInput) && !arrayControl[0]){
                    arrayControl[0] = true;
                    System.out.println("Je prends une carte");
                    players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                    continue;
                }
                if(useRegexPasser(userInput) && !arrayControl[1]){
                    arrayControl[1] = true;
                    System.out.println("Je passe");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Mauvaise saisie");
            }

        }
        return null;
    }

    private CouleurCarte chooseJokerCardColor() {

        String userInput;

        do {
            System.out.println("Quelle couleur attendue?");
            userInput = input.nextLine();
        }while(!CouleurCarte.contains(userInput));

        return(CouleurCarte.valueOf(userInput.toUpperCase()));
    }

    public void afficherScore(){
        for (Player player:players
        ) {
            System.out.println("Score du joueur : "+player.getScore() + " = " + player.getScore());
        }
    }

    public void applyCardEffect(CarteAbstrait carte){
        switch(carte.getType()){
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
                    /*
                    System.out.println("Tour de " + currentPlayer.getNom());
                    if(talon.getSommetTalon() instanceof CarteAction)
                     */
            }
            case CHANG_COULEUR -> {
                //choix couleur avant, casting carte???
            }
            case NOMBRE -> {
                indiceCurrentPlayer = incrIndiceCurrentPlayer(indiceCurrentPlayer, sensmanche, false);
            }
        }
    }

}
