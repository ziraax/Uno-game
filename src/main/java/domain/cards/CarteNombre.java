package main.java.domain.cards;

public class CarteNombre extends CarteAbstrait {
    private final int num;

    public CarteNombre(CouleurCarte couleur, int num) {
        super(TypeCarte.NOMBRE, couleur);
        this.num = num;
    }

    public int getNumCard(){
        return num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteNombre that = (CarteNombre) o;
        return num == that.num && getCouleur() == that.getCouleur();
    }

    @Override
    public String toString() {
        return "CarteNombre{" + getNumCard() + ", " + getCouleur() + '}';
    }

}
