package main.java.domain.game;

import main.java.domain.cards.CarteAbstrait;
import main.java.domain.interactions.Clavier;
import main.java.domain.player.Player;

import java.util.*;


public class Manche {

    public Player[] players;
    public Pioche pioche = new Pioche();
    public Talon talon = new Talon(pioche);



    public Manche(){
        Pioche pioche = new Pioche();
        Talon talon = new Talon(pioche);
        initialisationJoueurs();


    }

    public ArrayList<CarteAbstrait> dealCartesInit(){
        ArrayList<CarteAbstrait> init = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            init.add(i, prendreUneCarte());
        }
        return init;
    }

    public void initialisationJoueurs(){
        int nbJoueurs = 0;
        System.out.println("Veuillez rentrer le nombre de joueurs (2-10)");
        nbJoueurs = Clavier.lireEntier(2,10);
        players = new Player[nbJoueurs];

        for (int i = 0; i < nbJoueurs; i++) {
            String nom;
            boolean isNomUnique;

            do {
                System.out.println("Nom du joueur ?");
                nom = Clavier.lireChaine();
                isNomUnique = boolValidePseudo(nom, i);
                if (!isNomUnique) {
                    System.out.println("Le nom " + nom + "> est déja utilisé");
                }
            } while (!isNomUnique);

            players[i] = new Player(nom, dealCartesInit());

            /* PERMET D'AFFICHER TOUTES LES MAINS DE TOUS LES JOUEURS
            System.out.println(Arrays.toString(players[i].getMainJoueur().toArray()));
            */
        }

    }

    private boolean boolValidePseudo(String pseudoJoueur, int indice) {
        if (indice == 0) {
            return true;
        }
        for (int j = 0; j < indice; j++) {
            if (players[j].getNom().equalsIgnoreCase(pseudoJoueur)) {
                return false;
            }
        }
        return true;
    }

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
