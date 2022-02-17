package main.java.domain.game;

import main.java.domain.cards.CarteAbstrait;
import main.java.domain.cards.TypeCarte;

import java.util.ArrayList;
import java.util.List;

public class Talon {

    private final List<CarteAbstrait> talon = new ArrayList<>(108);

    public Talon(Pioche pioche){
        empiler(premiereCarteTalon(pioche));
    }

    public CarteAbstrait depiler() {
        return talon.remove(0);
    }

    public int nbCarte(){
        return talon.size();
    }

    public void empiler(CarteAbstrait carte) {
        talon.add(0, carte);
    }

    public CarteAbstrait premiereCarteTalon(Pioche pioche){
        
        CarteAbstrait carte;

        while(true){
            carte = pioche.depiler();
            if(carte.getType() == TypeCarte.valueOf("PLUS_QUATRE")){
                remelangerCarte(carte);
            } else {
                return carte;
            }
        }
    }

    private void remelangerCarte(CarteAbstrait carte){
        //to be written
        System.out.println("To be written");
    }

}
