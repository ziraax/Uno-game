package main.java.domain.cards;

public class CarteJoker extends CarteAbstrait {

    public CarteJoker(TypeCarte type){
        super(type, null);
    }

    public CarteJoker(TypeCarte type, CouleurCarte couleur){
        super(type, couleur);
    }

    // TODO: 17/02/2022  
    @Override
    public boolean isCompatible(CarteAbstrait carte) {
        if(getCouleur() == null){
            return false;
        }
        return(getCouleur() == carte.getCouleur());
    }

    public void setCouleurCarteJoker(CouleurCarte couleur){
        this.couleur = couleur;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteJoker that = (CarteJoker) o;
        return getType() == that.getType() && getCouleur() == that.getCouleur();
    }

    @Override
    public String toString() {
        return "CarteJoker{" + getType() + ", " + getCouleur() + "}\n";
    }

}
