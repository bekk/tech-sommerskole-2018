package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.logic.Fuzzy;

public class CityReq extends Requirement<String> {

    public CityReq(String val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        return Fuzzy.fuzzyScore(val, beer.getCity());
    }
}
