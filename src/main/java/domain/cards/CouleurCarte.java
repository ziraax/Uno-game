package main.java.domain.cards;

public enum CouleurCarte {
    JAUNE,
    BLEU,
    ROUGE,
    VERT;

    public static boolean contains(String s) {
        for(CouleurCarte couleurCarte:values())
            if (couleurCarte.name().equalsIgnoreCase(s))
                return true;
        return false;
    }

}


