package main.java.domain.interactions;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;


public class Interaction {

    private static final Scanner scanner = new Scanner( System.in );

    public static int readIntBetweenInterval(int min, int max ) {
        int n=0;
        boolean dummyBool;
        do {
            try {
                do{
                    Scanner sc = new Scanner(System.in);
                    n=sc.nextInt();
                    if(n < min || n > max){
                        System.out.println("Nombre hors borne ! Tu dois rentrer un nombre entre "+ min + " et " + max+".");
                    }
                }while(n < min || n > max);
                dummyBool=false;
            } catch(InputMismatchException e) {
                System.out.println("Erreur de saisie ! On te demande un nombre !");
                dummyBool=true;
            }
        }while(dummyBool);

        return(n);
    }

    public static String readString() {
        System.out.print( "? >> " );
        return scanner.nextLine();
    }

    /**
     * methode pour savoir si input correspond à /j x
     * @param input : string tapé par l'utilisateur
     * @return boolean : correspondance entre patterne et input
     */
    public static boolean isRegexNormalCard(final String input) {
        final Pattern pattern = Pattern.compile("/j\s[0-9]+", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * methode pour savoir si input correspond à /j x couleur
     * @param input : string tapé par l'utilisateur
     * @return boolean : correspondance entre patterne et input
     */
    public static boolean isRegexJokerCard(final String input) {
        final Pattern pattern = Pattern.compile("/j\\s[0-9]+\\s[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isRegexBluff(final String input) {
        final Pattern pattern = Pattern.compile("/b", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isRegexTakeCard(final String input) {
        final Pattern pattern = Pattern.compile("/p", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isRegexShowHand(final String input) {
        final Pattern pattern = Pattern.compile("/m", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isRegexPass(final String input) {
        final Pattern pattern = Pattern.compile("/e", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
