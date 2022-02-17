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

    /**
     *
     * @param pioche de la partie
     * @return la premiere carte du talon qui n'est pas un +4
     * si c'est le cas, on la remelange dans la pioche
     */
    public CarteAbstrait initTalonPremiereCarteNoPlus4(Pioche pioche){

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
