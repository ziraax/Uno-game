package main.java.domain.cards;

public class CarteAction extends CarteAbstrait {

    public CarteAction(TypeCarte type, CouleurCarte couleur){
        super(type, couleur);
        CarteMethodesUtiles.estActionValide(type);
        CarteMethodesUtiles.estCouleurValide(couleur);
    }

    // TODO: 17/02/2022  
    @Override
    public boolean isCompatible(CarteAbstrait carte) {

        boolean compatible = false;

        switch(getType()){
            case SKIP:
                if(carte.getType() == TypeCarte.SKIP)
                    compatible = true;
                if(carte.getCouleur() == getCouleur())
                    compatible = true;
                break;
            case REVERSE:
                if(carte.getType() == TypeCarte.REVERSE)
                    compatible = true;
                if(carte.getCouleur() == getCouleur())
                    compatible = true;
                break;
            case PLUS_DEUX:
                if(carte.getCouleur() == getCouleur())
                    compatible = true;
                break;
        }

        return compatible;
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
