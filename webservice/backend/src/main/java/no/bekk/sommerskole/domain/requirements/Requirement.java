package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public abstract class Requirement<T> {


    T val;
    double weight;

    Requirement(T val, double weight) {
        this.val = val;
        this.weight = weight;
    }

    public abstract double calculateCost(Beer beer);

    public double getWeight() {
        return weight;
    }
}



