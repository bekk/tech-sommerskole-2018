package no.bekk.sommerskole.domain;

public class Brewery {
    private int id;
    private String name;

    public Brewery() {
    }

    public int getId() {
        return id;
    }

    public Brewery setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brewery setName(String name) {
        this.name = name;
        return this;
    }
}
