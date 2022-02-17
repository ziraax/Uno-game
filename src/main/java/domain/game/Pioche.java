package main.java.domain.game;

import main.java.domain.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pioche {
    private final List<CarteAbstrait> pioche = new ArrayList<>(108);

    public Pioche(){
        creerCartesAction();
        creerCartesJoker();
        creerCartesNombre();
        Collections.shuffle(pioche);
    }

    private void creerCartesNombre() {
        for(var couleur : CouleurCarte.values()) {
            pioche.add(new CarteNombre(couleur, 0));

            for(var i = 1; i<=9;i++){
                pioche.add(new CarteNombre(couleur, i));
                pioche.add(new CarteNombre(couleur, i));
            }
        }
    }

    private void creerCartesAction() {
        for(var couleur : CouleurCarte.values()){
            for (var i = 0; i < 2; i++) {
                pioche.add(new CarteAction(TypeCarte.SKIP, couleur));
                pioche.add(new CarteAction(TypeCarte.REVERSE, couleur));
                pioche.add(new CarteAction(TypeCarte.PLUS_DEUX, couleur));
            }
        }
    }

    private void creerCartesJoker() {
        for (var i = 0; i < 4 ; i++) {
            pioche.add(new CarteJoker(TypeCarte.PLUS_QUATRE));
            pioche.add(new CarteJoker(TypeCarte.CHANG_COULEUR));
        }
    }

    public CarteAbstrait depiler() {
        return pioche.remove(0);
    }

    public int nbCarte(){
        return pioche.size();
    }

    public void empiler(CarteAbstrait carte) {
        pioche.add(0, carte);
    }


}
