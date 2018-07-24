package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public abstract class Requirement<T> {


    protected T val;
    protected double weight;

    public Requirement(T val, double weight) {
        this.val = val;
        this.weight = weight;
    }

    public abstract double calculateCost(Beer beer);

}



