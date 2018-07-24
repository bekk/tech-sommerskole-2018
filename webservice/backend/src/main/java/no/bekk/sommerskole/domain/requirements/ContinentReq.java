package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public class ContinentReq extends Requirement<String> {

    public ContinentReq(String val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        return val.toLowerCase().equals(beer.getCountry().getContinent().toLowerCase()) ? 1 : 0;
    }
}
