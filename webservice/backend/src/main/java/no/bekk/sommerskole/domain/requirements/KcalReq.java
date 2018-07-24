package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public class KcalReq extends Requirement<Double> {

    public KcalReq(Double val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        if(val == null || beer.getKcal() == null) return 0;
        return 1/Math.abs(beer.getKcal()-val);
    }

}
