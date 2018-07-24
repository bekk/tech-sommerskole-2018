package no.bekk.sommerskole.domain;

import no.bekk.sommerskole.logic.ImageSelector;

import java.time.LocalDateTime;

public class BeerDetails {
    private int id;
    private String name;
    private Brewery brewery;
    private Double abv;
    private Country country;
    private Integer ibu;
    private String webpage;
    private Double kcal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String image;

    public BeerDetails() {
    }

    public int getId() {
        return id;
    }

    public BeerDetails setId(int id) {
        this.image = ImageSelector.getRandomBeerImage(id);
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BeerDetails setName(String name) {
        this.name = name;
        return this;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public BeerDetails setBrewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public double getAbv() {
        return abv;
    }

    public BeerDetails setAbv(double abv) {
        this.abv = abv;
        return this;
    }

    public BeerDetails setAbv(Double abv) {
        this.abv = abv;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public BeerDetails setCountry(Country country) {
        this.country = country;
        return this;
    }

    public Integer getIbu() {
        return ibu;
    }

    public BeerDetails setIbu(Integer ibu) {
        this.ibu = ibu;
        return this;
    }

    public String getWebpage() {
        return webpage;
    }

    public BeerDetails setWebpage(String webpage) {
        this.webpage = webpage;
        return this;
    }

    public Double getKcal() {
        return kcal;
    }

    public BeerDetails setKcal(Double kcal) {
        this.kcal = kcal;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BeerDetails setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BeerDetails setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getImage() {
        return image;
    }
}
