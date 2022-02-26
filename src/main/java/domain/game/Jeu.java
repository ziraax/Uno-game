package main.java.domain.game;

import main.java.domain.interactions.Clavier;
import main.java.domain.player.Player;

public class Jeu {

    public Player[] players;
    final static int MAXPOINT = 500;

    public Jeu(){
        initialisationJoueurs();
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

    }


    /**
     * permet d'initialiser le jeu en dsitribuant les cartes et en donnnt un psuedo à tout le monde
     * ainsi qu'en initialisant le score a de 0 de tout le monde
     */
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

            /*
            System.out.println(Arrays.toString(players[i].getMainJoueur().toArray()));
             */

        }
    }

    /**
     *
     * @param pseudoJoueur a verifier
     * @param indice du joueur dans le tableau de joueur
     * @return un booleen qui dit si le pseudo est valide (<=> unique)
     */
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
}
