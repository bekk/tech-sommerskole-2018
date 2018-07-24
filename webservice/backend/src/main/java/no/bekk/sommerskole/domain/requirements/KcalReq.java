package no.bekk.sommerskole.domain.requirements;

import no.bekk.sommerskole.domain.Beer;

public class KcalReq extends Requirement<Double> {

    public KcalReq(Double val, double wgt) {
        super(val, wgt);
    }

    @Override
    public double calculateCost(Beer beer) {
        return 0;
    }

}
