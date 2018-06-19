package no.bekk.sommerskole.domain;

public class Beer {
    private int id;
    private String name;
    private Brewery brewery;
    private float abv;

    public Beer() {
    }

    public Beer(int id, String name, Brewery brewery, float abv) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
        this.abv = abv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }
}
