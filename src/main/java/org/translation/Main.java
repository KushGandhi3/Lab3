package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static final String QUIT = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator();
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            LanguageCodeConverter lconverter = new LanguageCodeConverter();
            CountryCodeConverter cconverter = new CountryCodeConverter();
            if (QUIT.equals(country)) {
                break;
            }
            String countrycode = cconverter.fromCountry(country);
            String language = promptForLanguage(translator, countrycode);
            if (QUIT.equals(language)) {
                break;
            }
            String languagecode = lconverter.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator
                    .translate(countrycode, languagecode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        List<String> countriesNames = new ArrayList<>();
        CountryCodeConverter converter = new CountryCodeConverter();

        // Converting Country Codes to Country Names
        for (String country : countries) {
            countriesNames.add(converter.fromCountryCode(country));
        }

        // Sorting Country Names
        Collections.sort(countriesNames);

        // Printing Country Names
        for (String country : countriesNames) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> languages = translator.getCountryLanguages(country);
        List<String> languageNames = new ArrayList<>();
        LanguageCodeConverter converter = new LanguageCodeConverter();

        // Converting Language Codes to Language Names
        for (String language : languages) {
            languageNames.add(converter.fromLanguageCode(language));
        }

        // Sorting Language Names
        Collections.sort(languageNames);

        // Printing Language Names
        for (String language : languageNames) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
