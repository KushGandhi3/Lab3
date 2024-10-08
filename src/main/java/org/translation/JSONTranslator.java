package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private Map<String, Map<String, String>> countrylangs = new HashMap<>();
    private ArrayList<String> countrycodes = new ArrayList<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryobj = jsonArray.getJSONObject(i);

                // Parsing Data
                String countrycode = countryobj.getString("alpha3");
                Map<String, String> langs = new HashMap<>();

                for (String langcode : countryobj.keySet()) {
                    if (!"id".equals(langcode) && !"alpha2".equals(langcode) && !"alpha3".equals(langcode)) {
                        langs.put(langcode, countryobj.getString(langcode));
                    }
                }

                countrycodes.add(countrycode);
                countrylangs.put(countrycode, langs);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        Map<String, String> languages = countrylangs.get(country);
        if (languages == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(languages.keySet());
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countrycodes);
    }

    @Override
    public String translate(String country, String language) {
        Map<String, String> translations = countrylangs.get(country);
        if (translations == null) {
            return null;
        }
        return translations.get(language);
    }
}
