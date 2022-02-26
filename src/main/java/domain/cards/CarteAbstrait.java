package main.java.domain.cards;

public abstract class CarteAbstrait implements Carte {
    protected final TypeCarte type;
    protected CouleurCarte couleur;

    protected CarteAbstrait(TypeCarte type, CouleurCarte couleur){
        this.type = type;
        this.couleur = couleur;
    }

    @Override
    public TypeCarte getType() {
        return type;
    }

    @Override
    public CouleurCarte getCouleur() {
        return couleur;
    }


    /**
     *
     * @param carte
     * @return boolean de compatibilité
     */
    public abstract boolean isCompatible(CarteAbstrait carte);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();


}
