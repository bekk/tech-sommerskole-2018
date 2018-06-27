package no.bekk.sommerskole.domain;

public class Country {
    private String countryCode;
    private String name;
    private String continent;
    private int numberOfBeers;

    public Country() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public String getContinent() {
        return continent;
    }

    public Country setContinent(String continent) {
        this.continent = continent;
        return this;
    }

    public int getNumberOfBeers() {
        return numberOfBeers;
    }

    public Country setNumberOfBeers(int numberOfBeers) {
        this.numberOfBeers = numberOfBeers;
        return this;
    }
}
