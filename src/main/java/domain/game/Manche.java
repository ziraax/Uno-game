package main.java.domain.game;


import main.java.domain.cards.*;
import main.java.domain.player.Player;

import java.util.*;

import static main.java.domain.interactions.Interaction.*;


public class Manche {

    public Pioche pioche = new Pioche();
    public Talon talon = new Talon(pioche);
    public SensJeu sensmanche;

    public Player[] players;
    private int indiceCurrentPlayer;
    private boolean hasPlayerPickACard;

    static Scanner input = new Scanner(System.in);

    public Manche(Player[] players) {

        this.players = players;
        Pioche pioche = new Pioche();
        Talon talon = new Talon(pioche);
        sensmanche = SensJeu.HORAIRE;

        Random r = new Random();
        indiceCurrentPlayer = r.nextInt(players.length);

        System.out.println("Distribution des cartes !");
        dealCartes();

    }

    public void lancerUneManche() {
        afficherInfoCommande();
        System.out.println("La manche commence !");

        // TODO: 05/03/2022 regler le soucis pour le changement de couleur premiere carte
        if (checkIfFirstCardIsJoker(talon.getSommetTalon())) {
            System.out.println("La carte au sommet du talon est : " + talon.getSommetTalon());
            System.out.println("La main du joueur " + players[indiceCurrentPlayer].getNom() + " est : \n" + players[indiceCurrentPlayer].getMainJoueur().toString());
            ((CarteJoker) talon.getSommetTalon()).setCouleurCarteJoker(chooseJokerCardColor());
        } else {
            applyCardEffect(talon.getSommetTalon());
        }

        while (!hasSomeoneNoCard()) {

            hasPlayerPickACard = false;

            System.out.print("La carte au sommet du talon est : " + talon.getSommetTalon());
            System.out.println(players[indiceCurrentPlayer].getNom() + " à vous de jouer !");

            CarteAbstrait carte_choisi = ReadCommandAndPlay();


            if (!(carte_choisi == null)) {
                applyCardEffect(carte_choisi);
            } else {
                moveToNextPlayer();
            }

        }
        setPointsForAllPlayers();
        afficherScore();
        resetHands();
    }

    public void moveToNextPlayer() {
        switch (sensmanche) {
            case HORAIRE -> {
                if (indiceCurrentPlayer < players.length - 1) {
                    indiceCurrentPlayer++;
                } else {
                    indiceCurrentPlayer = 0;
                }
            }
            case ANTI_HORAIRE -> {
                if (indiceCurrentPlayer != 0) {
                    indiceCurrentPlayer--;
                } else {
                    indiceCurrentPlayer = players.length - 1;
                }
            }
        }

    }

    public void applyCardEffect(CarteAbstrait carte) {
        switch (carte.getType()) {
            case SKIP -> {
                moveToNextPlayer();
                moveToNextPlayer();
            }
            case REVERSE -> {
                if (sensmanche == SensJeu.HORAIRE) {
                    sensmanche = SensJeu.ANTI_HORAIRE;
                } else {
                    sensmanche = SensJeu.HORAIRE;
                }
                moveToNextPlayer();
            }
            case PLUS_DEUX -> {
                moveToNextPlayer();
                for (int i = 0; i < 2; i++) {
                    players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                }
                moveToNextPlayer();
            }
            case PLUS_QUATRE -> {
                int indiceLastPlayer = indiceCurrentPlayer;
                moveToNextPlayer();

                //bluff scenario
                while(true){
                    try {
                        System.out.println("Entrez /b si vous pensez que :" + players[indiceLastPlayer].getNom()
                                            + "vient de bluffer. N'importe quoi d'autre sinon.");
                        String userInput = input.nextLine();
                        if(isRegexBluff(userInput)){
                            boolean isGuilty = false;
                            //temp
                            CarteAbstrait lastCard = talon.depiler();
                            CarteAbstrait beforeLastCard = talon.depiler();

                            for (int i = 0; i < players[indiceLastPlayer].nbCartesMain(); i++) {
                                if(beforeLastCard.isCompatible(players[indiceLastPlayer].getMainJoueur().get(i))){
                                    isGuilty = true;
                                    break;
                                }
                            }
                            if(isGuilty){
                                System.out.println("Le joueur " + players[indiceLastPlayer].getNom() + " est coupable ! ");
                                System.out.println("Penalité de 4 cartes...");
                                for (int i = 0; i < 4; i++) {
                                    players[indiceLastPlayer].getMainJoueur().add(prendreUneCarte());
                                }
                            } else {
                                System.out.println("Le joueur " + players[indiceLastPlayer].getNom() + " est innocent ! ");
                                System.out.println("Le joueur " + players[indiceCurrentPlayer].getNom() + " tire 6 cartes au lieu de 4...");
                                for (int i = 0; i < 6; i++) {
                                    players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                                }
                            }

                            talon.empiler(beforeLastCard);
                            talon.empiler(lastCard);

                            break;
                        } else {
                            for (int i = 0; i < 4; i++) {
                                players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                            }
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("On ne comprend pas votre commande, ré-essayez !");
                    }
                }


                moveToNextPlayer();
            }
            case CHANG_COULEUR, NOMBRE -> moveToNextPlayer();
            default -> moveToNextPlayer();
        }
    }

    // TODO: 05/03/2022 A VERIFIER
    public boolean checkIfFirstCardIsJoker(CarteAbstrait carteInit) {
        return carteInit.getType() == TypeCarte.CHANG_COULEUR;
    }

    public CarteAbstrait ReadCommandAndPlay() {
        while (true) {
            int idx_carte;
            CarteAbstrait carte_choisi;

            String userInput = input.nextLine();
            boolean isRecognized = isRegexJokerCard(userInput) || isRegexNormalCard(userInput)
                    || isRegexShowHand(userInput) || isRegexTakeCard(userInput) || isRegexPass(userInput);
            try {
                if (isRecognized) {
                    if (isRegexJokerCard(userInput)) {

                        idx_carte = (userInput.charAt(3) - '1');
                        carte_choisi = players[indiceCurrentPlayer].getMainJoueur().get(idx_carte);
                        String couleur_choisi = userInput.substring(5).trim();

                        if (carte_choisi instanceof CarteJoker) {

                            ((CarteJoker) carte_choisi).setCouleurCarteJoker(CouleurCarte.valueOf(couleur_choisi.toUpperCase()));
                            talon.empiler(players[indiceCurrentPlayer].retirerCarte(idx_carte));
                            affichageCarteChoisi(carte_choisi);

                            return carte_choisi;
                        } else {
                            System.out.println("Desolé cette carte n'est pas valide.");
                        }
                        continue;
                    }
                    if (isRegexNormalCard(userInput)) {

                        idx_carte = (userInput.charAt(3) - '1');
                        int print_idx = idx_carte + 1;
                        carte_choisi = players[indiceCurrentPlayer].getMainJoueur().get(idx_carte);

                        if (carte_choisi.isCompatible(talon.getSommetTalon()) || carte_choisi instanceof CarteJoker) {

                            if (carte_choisi instanceof CarteJoker) {
                                System.out.println("C'est une carte Joker !" + "Vous devez rentrer /j " + print_idx + " couleur");
                                ((CarteJoker) carte_choisi).setCouleurCarteJoker(chooseJokerCardColor());
                            }

                            talon.empiler(players[indiceCurrentPlayer].retirerCarte(idx_carte));
                            affichageCarteChoisi(carte_choisi);

                            return carte_choisi;
                        } else {
                            System.out.println("Desolé cette carte n'est pas valide.");
                        }
                        continue;
                    }
                    if (isRegexShowHand(userInput)) {
                        System.out.println("La main du joueur " + players[indiceCurrentPlayer].getNom() + " est : \n" + players[indiceCurrentPlayer].getMainJoueur().toString());
                        continue;
                    }
                    if (isRegexTakeCard(userInput)) {
                        if (!hasPlayerPickACard) {
                            System.out.println("Le joueur " + players[indiceCurrentPlayer].getNom() + " prend une carte.");
                            players[indiceCurrentPlayer].getMainJoueur().add(prendreUneCarte());
                            hasPlayerPickACard = true;
                        } else {
                            System.out.println("Vous avez déjà pioché une carte !");
                        }
                        continue;
                    }
                    if (isRegexPass(userInput)) {
                        System.out.println("Le joueur " + players[indiceCurrentPlayer].getNom() + " passe.");
                        break;
                    }
                } else {
                    System.out.println("On ne comprend pas votre commande, ré-essayez !");
                }
            } catch (Exception e) {
                System.out.println("On ne comprend pas votre commande, ré-essayez !");
            }

        }
        return null;
    }

    public boolean hasSomeoneNoCard() {
        for (Player player : players
        ) {
            if (player.getMainJoueur().size() == 0) {
                return true;
            }
        }
        return false;
    }

    public void setPointsForAllPlayers() {
        for (Player player : players
        ) {
            int score = player.getScore();

            for (int i = 0; i < player.nbCartesMain(); i++) {
                if (player.getMainJoueur().get(i) instanceof CarteNombre) {
                    score += ((CarteNombre) player.getMainJoueur().get(i)).getNumCard();
                }
                if (player.getMainJoueur().get(i) instanceof CarteAction) {
                    score += 20;
                }
                if (player.getMainJoueur().get(i) instanceof CarteJoker) {
                    score += 50;
                }
            }
            player.setScore(score);
        }
    }

    //DELETE
    private void informationJeu() {
        for (Player player : players
        ) {
            System.out.println(Arrays.toString(player.getMainJoueur().toArray()) + player.getNom());
        }
    }

    public void dealCartes() {
        for (int nbCarte = 0; nbCarte < 6; nbCarte++) {
            for (Player player : players
            ) {
                player.ajoutCarte(pioche.depiler());
            }
        }
    }

    /**
     * @return la carte tirée de la pioche
     */
    public CarteAbstrait prendreUneCarte() {
        if (pioche.nbCarte() == 0) {
            /* ATTENTION IL Y A LE SHUFFLE IL FAUDRA TROUVER UNE SOLUTION */
            System.out.println("Pioche vide!");

            CarteAbstrait sommetTalon = talon.depiler();

            while (talon.nbCarte() != 0) {
                pioche.empiler(talon.depiler());
            }
            Collections.shuffle((List<?>) pioche);
            talon.empiler(sommetTalon);
        }
        return pioche.depiler();
    }

    private CouleurCarte chooseJokerCardColor() {
        String userInput;
        do {
            System.out.println("Quelle couleur attendue?");
            userInput = input.nextLine();
            if(!CouleurCarte.contains(userInput)){
                System.out.println("Cette couleur n'existe pas !");
            }
        } while (!CouleurCarte.contains(userInput));

        return (CouleurCarte.valueOf(userInput.toUpperCase()));
    }

    public void afficherScore() {
        for (Player player : players
        ) {
            System.out.println("Score du joueur : " + player.getNom() + " = " + player.getScore());
        }
    }

    public void afficherInfoCommande() {
        System.out.println("Pour afficher la main du joueur, tapez /m");
        System.out.println("Pour piocher une carte, tapez /p");
        System.out.println("Pour jouer une carte, tapez /j n");
        System.out.println("Pour passer votre tour, tapez /e");
        System.out.println("\n");
    }

    public void affichageCarteChoisi(CarteAbstrait carte_choisi) {
        if (carte_choisi instanceof CarteNombre) {
            System.out.println("Le joueur " + players[indiceCurrentPlayer].getNom() + " joue un " + ((CarteNombre) carte_choisi).getNumCard() + " de couleur " + carte_choisi.getCouleur());
        } else {
            System.out.println("Le joueur " + players[indiceCurrentPlayer].getNom() + " joue un " + carte_choisi.getType() + " de couleur " + carte_choisi.getCouleur());
        }
    }

    public void resetHands() {
        for (Player player : players
        ) {
            for (int i = 0; i < player.nbCartesMain(); i++) {
                pioche.empiler(player.retirerCarte(i));
            }
        }
    }

}
