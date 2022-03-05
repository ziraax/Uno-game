package main.java.domain.interactions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.domain.cards.Carte;


public class Clavier {

    /**
     * grace à cet objet on peut lire à partir du clavier
     */
    private static final Scanner scanner = new Scanner( System.in );

    /**
     * permet de lire un entier à partir du clavier
     * @return l'entier lu
     */
    public static int lireEntier() {
        System.out.print( "? >> " );
        int res = scanner.nextInt();
        scanner.nextLine(); // pour consommer le caractére \n à la fin de la chaîne
        // sinon, on va avoir des problèmes si on souhaite ensuite lire une chaîne de caractères !
        // http://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-nextint-or-other-nextfoo
        return res;
    }

    /**
     * permet de lire un entier entre min et max
     * @param min	la borne inférieure de l'entier à lire
     * @param max	la borne supérieure de l'entier à lire
     * @return		l'entier qu'on veut lire du clavier
     */
    public static int lireEntier( int min, int max ) {
        if ( min > max ) {
            // inverser les bornes
            int temp = min;
            min = max;
            max = temp;
        }
        boolean horsBornes;
        int num;
        do {
            num = lireEntier();
            horsBornes = num < min || num > max;
            if ( horsBornes ) {
                System.out.println( "indice hors bornes ! [" + min + ".." + max + "]" );
            }
        } while ( horsBornes );
        return num;
    }

    /**
     * permet de lire un entier qui représente l'une des cases de la liste arrayList
     * @param arrayList la liste dont on veut lire un indice valide
     * @return l'indice de l'une des cases de la liste arrayList
     */
    public static int lireEntier( ArrayList<Carte> arrayList ) {
        if ( arrayList.isEmpty() ) {
            return -1;
        }
        return lireEntier( 0, arrayList.size() - 1 ); // les listes sont indéxés de 0 à nbElements - 1
    }

    /**
     * permet de lire une chaîne de caractères à partir du clavier
     * @return la chaîne lue
     */
    public static String lireChaine() {
        System.out.print( "? >> " );
        return scanner.nextLine();
    }

    /**
     * permer de fermer la variable scanner à la fin du programme
     */
    public static void fermer() {
        scanner.close();
    }

    public static boolean useRegexNormalCard(final String input) {
        final Pattern pattern = Pattern.compile("/j\s[0-9]+", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean useRegexShowHand(final String input) {
        final Pattern pattern = Pattern.compile("/m", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean useRegexTakeCard(final String input) {
        final Pattern pattern = Pattern.compile("/p", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean useRegexPasser(final String input) {
        final Pattern pattern = Pattern.compile("/e", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean useRegexJokerCard(final String input) {
        final Pattern pattern = Pattern.compile("/j\\s[0-9]+\\s[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
