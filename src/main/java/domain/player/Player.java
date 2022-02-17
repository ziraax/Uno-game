package main.java.domain.player;

import main.java.domain.cards.Carte;
import main.java.domain.cards.CarteAbstrait;

import java.util.ArrayList;

public class Player {
    private final String nom;
    private final ArrayList<CarteAbstrait> mainJoueur;

    public Player(String nom, ArrayList<CarteAbstrait> mainJoueur) {
        super();
        this.nom = nom;
        this.mainJoueur = mainJoueur;
    }

    public String getNom() {
        return nom;
    }

    public void ajoutCarte(CarteAbstrait Carte){
        mainJoueur.add(Carte);
    }

    public CarteAbstrait retirerCarte(int num) {
        return mainJoueur.remove(num);
    }

    public ArrayList<CarteAbstrait> getMainJoueur() {
        return mainJoueur;
    }



    public int nbCartesMain() {
        return mainJoueur.size();
    }






    //prendreCarte
    //jouerTour
    //donnerCouleur



}
