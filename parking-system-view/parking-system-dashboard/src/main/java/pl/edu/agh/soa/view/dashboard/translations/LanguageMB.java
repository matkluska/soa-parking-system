package pl.edu.agh.soa.view.dashboard.translations;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
@SessionScoped
public class LanguageMB {
    private Locale locale;

    private static Map<String, Locale> countries;

    static {
        countries = new LinkedHashMap<>();
        countries.put("Polski", Locale.forLanguageTag("pl"));
        countries.put("English", Locale.ENGLISH);
    }

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public Map<String, Locale> getCountriesInMap() {
        return countries;
    }


    public Locale getLocale() {
        return locale;
    }


    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = Locale.forLanguageTag(language);
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();

        //loop country map to compare the locale code
        for (Map.Entry<String, Locale> entry : countries.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {

                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale(entry.getValue());

            }
        }
    }
}
