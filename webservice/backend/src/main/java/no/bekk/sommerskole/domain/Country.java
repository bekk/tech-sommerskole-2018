package no.bekk.sommerskole.domain;

import java.util.Objects;

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

    @Override
    public String toString() {
        return String.format("Country('%s')", this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getNumberOfBeers() == country.getNumberOfBeers() &&
                Objects.equals(getCountryCode(), country.getCountryCode()) &&
                Objects.equals(getName(), country.getName()) &&
                Objects.equals(getContinent(), country.getContinent());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCountryCode(), getName(), getContinent(), getNumberOfBeers());
    }
}