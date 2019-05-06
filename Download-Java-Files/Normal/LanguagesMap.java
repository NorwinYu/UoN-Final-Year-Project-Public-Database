package org.orcid.frontend.web.util;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.orcid.jaxb.model.common.LanguageCode;
import org.orcid.persistence.constants.SiteConstants;
import org.springframework.cache.annotation.Cacheable;

public class LanguagesMap {
    private static final String X = "X";      
    
    /**
     * This map contains all the available languages in all available locales.
     * The structure looks like this:
     * 
     * For each Locale that already accessed the site, a new map will be
     * created, that map contains a name of the form "Language(Country)" with a
     * key of the form "language-code_country-code". If the local contains
     * variants, the name will look like
     * "Language Variant (Country) and the key will look like "
     * language-code_country-code_variant_code"
     * */
    private static Map<String, Map<String, String>> i18nLanguagesMap = new TreeMap<String, Map<String, String>>();

    private static final Locale[] orcidLocales = getLanguages();

    /* get all ISO languages, remove zh and add in zh_TW and zh_CN */ 
    static private Locale[] getLanguages() {
        LanguageCode[] codes = LanguageCode.values();
        Locale[] orcidCodes = new Locale[codes.length];
        for (int i = 0; i< codes.length; i++) {
            orcidCodes[i] = LocaleUtils.toLocale(codes[i].name());           
        }
        return orcidCodes;
    }
    
    /**
     * Return a map that contains the list of available languages
     * 
     * @param locale
     *            The current locale
     * @return A map that contains the list of available languages in the
     *         current locale
     * */    
    public Map<String, String> getLanguagesMap(Locale locale) {
    	if (locale == null)
            locale = Locale.US;

        if (i18nLanguagesMap.containsKey(locale.toString()))
            return i18nLanguagesMap.get(locale.toString());
        else {
            Map<String, String> newLanguageMap = buildLanguageMap(locale, true);
            i18nLanguagesMap.put(locale.toString(), newLanguageMap);
            return newLanguageMap;
        }
    }    
    
    /**
     * Builds a map that contains the available languages for the given locale
     * 
     * @param userLocale
     *            the current locale
     * @return A map containing the available languages for the given locale
     * */    
    @Cacheable(value = "languages-map", key = "#userLocale.toString() + '-' + #sorted.toString()")
    public Map<String, String> buildLanguageMap(Locale userLocale, boolean sorted) {
    	Map<String, String> languagesMap = new TreeMap<String, String>();

        for (Locale locale: orcidLocales) {
            if(sorted)
                // It is ordered backwards to keep it sorted by language and country
                languagesMap.put(buildLanguageValue(locale, userLocale), locale.toString());
            else 
                languagesMap.put(locale.toString(), buildLanguageValue(locale, userLocale));
        }

        return languagesMap;
    }

    /**
     * Returns the language translated in the given user locale
     * 
     * @param locale
     * @param userLocal
     * 
     * @return The language translated to the given locale.
     * */
    @Cacheable(value = "languages-map", key = "#locale.toString() + '-' + #userLocale.toString()")
    public String buildLanguageValue(Locale locale, Locale userLocale) {     
        if(userLocale != null && userLocale.getLanguage().equals("xx")) {
            return X;
        }
    	String variant = locale.getVariant();
        String displayVariant = locale.getDisplayVariant(userLocale);
        String language = WordUtils.capitalize(locale.getDisplayLanguage(userLocale));

        if (StringUtils.isEmpty(variant))
            if (StringUtils.isEmpty(locale.getDisplayCountry(userLocale)))
                return language;
            else
                return language + " (" + locale.getDisplayCountry(userLocale) + ')';
        else if (StringUtils.isEmpty(locale.getDisplayCountry(userLocale)))
            return language + ' ' + displayVariant;
        else
            return language + ' ' + displayVariant + " (" + locale.getDisplayCountry(userLocale) + ')';
    }
}
