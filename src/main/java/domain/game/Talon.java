package main.java.domain.game;

import main.java.domain.cards.CarteAbstrait;
import main.java.domain.cards.TypeCarte;

import java.util.ArrayList;
import java.util.List;

public class Talon {

    private final List<CarteAbstrait> talon = new ArrayList<>(108);

    public Talon(Pioche pioche){
        empiler(initTalonPremiereCarteNoPlus4(pioche));
    }

    public CarteAbstrait depiler() {
        return talon.remove(0);
    }

    public int nbCarte(){
        return talon.size();
    }

    public CarteAbstrait getSommetTalon(){
        return talon.get(0);
    }

    public void empiler(CarteAbstrait carte) {
        talon.add(0, carte);
    }

    private CarteAbstrait initTalonPremiereCarteNoPlus4(Pioche pioche){

        for (int i = 0; i < pioche.nbCarte(); i++) {
            CarteAbstrait carte = pioche.getCardFromPioche(i);
            if(!(carte.getType() == TypeCarte.PLUS_QUATRE)){
                return carte;
            }
        }
        return null;
    }

}
