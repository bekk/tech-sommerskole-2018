package no.bekk.sommerskole.domain;

import java.util.Objects;

public class Beer {
    private int id;
    private String name;
    private Brewery brewery;
    private Double abv;
    private Country country;
    private String city;
    private Double kcal;

    public Beer(int id, String name, Brewery brewery, Double abv, Country country, String city) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
        this.abv = abv;
        this.country = country;
        this.city = city;
    }

    public Beer() {
    }

    public int getId() {
        return id;
    }

    public Beer setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Beer setName(String name) {
        this.name = name;
        return this;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public Beer setBrewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public Double getAbv() {
        return abv;
    }

    public Beer setAbv(double abv) {
        this.abv = abv;
        return this;
    }

    public Beer setAbv(Double abv) {
        this.abv = abv;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Beer setCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Beer setCity(String city) {
        this.city = city;
        return this;
    }

    public Double getKcal() {
        return kcal;
    }

    public Beer setKcal(Double kcal){
        this.kcal = kcal;
        return this;
    }

    @Override
    public String toString() {
        return "Beer{" +
                ", name='" + name + '\'' +
                ", brewery=" + brewery +
                ", abv=" + abv +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return getId() == beer.getId() &&
                Objects.equals(getName(), beer.getName()) &&
                Objects.equals(getBrewery(), beer.getBrewery()) &&
                Objects.equals(getAbv(), beer.getAbv()) &&
                Objects.equals(getCountry(), beer.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBrewery(), getAbv(), getCountry());
    }
}
