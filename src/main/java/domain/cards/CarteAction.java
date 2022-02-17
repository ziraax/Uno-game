package main.java.domain.cards;

public class CarteAction extends CarteAbstrait {

    public CarteAction(TypeCarte type, CouleurCarte couleur){
        super(type, couleur);
        CarteMethodesUtiles.estActionValide(type);
        CarteMethodesUtiles.estCouleurValide(couleur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteAction that = (CarteAction) o;
        return getType() == that.getType() && getCouleur() == that.getCouleur();
    }

    @Override
    public String toString() {
        return "CarteAction{" + getType() + ", " + getCouleur() + "}";
    }
}
