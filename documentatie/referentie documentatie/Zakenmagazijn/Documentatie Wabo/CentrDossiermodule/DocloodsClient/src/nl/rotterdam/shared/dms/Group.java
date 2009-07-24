package nl.rotterdam.shared.dms;

import java.util.List;

public class Group {
    private String name;
    private String abbreviation;
    private boolean selected;
    private List<Gebruiker> gebruikers;
    
    // constructors
    public Group() {
      selected = false;
    }

    // getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setGebruikers(List<Gebruiker> gebruikers) {
        this.gebruikers = gebruikers;
    }

    public List<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
