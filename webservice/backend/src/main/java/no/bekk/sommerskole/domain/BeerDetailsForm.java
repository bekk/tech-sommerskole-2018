package no.bekk.sommerskole.domain;

public class BeerDetailsForm {

    private int id;
    private String name;
    private int brewery;
    private String country;
    private Integer ibu;
    private Float abv;
    private Float kcal;
    private String webpage;

    public BeerDetailsForm(int id, String name, int brewery, String country, Integer ibu, Float abv, Float kcal, String webpage) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
        this.country = country;
        this.ibu = ibu;
        this.abv = abv;
        this.kcal = kcal;
        this.webpage = webpage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBrewery() {
        return brewery;
    }

    public Float getAbv() {
        return abv;
    }

    public String getCountry() {
        return country;
    }

    public Integer getIbu() {
        return ibu;
    }

    public String getWebpage() {
        return webpage;
    }

    public Float getKcal() {
        return kcal;
    }
}
