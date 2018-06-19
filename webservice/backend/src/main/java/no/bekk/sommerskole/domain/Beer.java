package no.bekk.sommerskole.domain;

public class Beer {
    private int id;
    private String name;
    private Brewery brewery;
    private Float abv;
    private Country country;

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

    public float getAbv() {
        return abv;
    }

    public Beer setAbv(float abv) {
        this.abv = abv;
        return this;
    }

    public Beer setAbv(Float abv) {
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
}