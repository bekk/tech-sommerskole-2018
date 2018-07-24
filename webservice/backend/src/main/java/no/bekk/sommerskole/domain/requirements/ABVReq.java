package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public class ABVReq extends Requirement<Double> {

    public ABVReq(Double val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        return val != null ? 1/Math.abs(beer.getAbv()-val) : 0;
    }
}
