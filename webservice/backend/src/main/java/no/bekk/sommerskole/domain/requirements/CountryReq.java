package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

import java.util.List;

public class CountryReq extends Requirement<List<String>> {

    public CountryReq(List<String> val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        return val.contains(beer.getCountry().getCountryCode()) ? 1 : 0;
    }
}
