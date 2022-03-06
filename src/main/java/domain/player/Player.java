package main.java.domain.player;

import main.java.domain.cards.Carte;
import main.java.domain.cards.CarteAbstrait;

import java.util.ArrayList;

public class Player {
    private String nom;
    private final ArrayList<CarteAbstrait> mainJoueur;
    private int score;

    public Player(String nom, int score) {
        super();
        this.nom = nom;
        mainJoueur = new ArrayList<>();
        this.score = score;
    }

    public void setNom(String s){
        this.nom = s;
    }

    public void setScore(int s){
        this.score = s;
    }

    public int getScore() {
        return score;
    }

    public String getNom() {
        return nom;
    }

    /**
     * ajoute une carte dans la main du joueur
     * @param Carte a ajouter
     */
    public void ajoutCarte(CarteAbstrait Carte){
        mainJoueur.add(Carte);
    }

    /**
     * retire une carte de la main du joueur et la retourne pour pouvoir l'empiler autre part
     * @param num l'index de la carte dans la main
     * @return la carte choisie
     */
    public CarteAbstrait retirerCarte(int num) {
        return mainJoueur.remove(num);
    }

    public ArrayList<CarteAbstrait> getMainJoueur() {
        return mainJoueur;
    }

    public int nbCartesMain() {
        return mainJoueur.size();
    }

}
