package main.java.domain.cards;

public class CarteMethodesUtiles {
    private CarteMethodesUtiles(){

    }

    public static void estCouleurValide(CouleurCarte couleur){
        if(couleur == null ) {
            throw new IllegalArgumentException("Couleur non definie");
        }
    }

    public static void estActionValide(TypeCarte type){
        if(     type == TypeCarte.SKIP ||
                type == TypeCarte.REVERSE ||
                type == TypeCarte.PLUS_DEUX
        ) {
            return;
        }

        throw new IllegalArgumentException("Pas une action");
    }

    public static boolean estCarteChangementCouleurPlusQuatre(Carte carte){
        return(carte.getType() == TypeCarte.CHANG_COULEUR || carte.getType() == TypeCarte.PLUS_QUATRE);
    }

    public static void estNombreValide(int num){
        if(num < 0 || num >9){
            throw new IllegalArgumentException("Numero pas dans la range [[0,9]]");
        }
    }

}
