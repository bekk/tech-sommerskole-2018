package no.bekk.sommerskole.suggester;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.requirements.RequirementsForm;
import no.bekk.sommerskole.filter.BeerFilter;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class Suggester {

    private BeerRepository beerRepository;

    @Inject
    public Suggester(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public List<Beer> suggestBeer(RequirementsForm form) {

        // Sugggester-oppgaven: Her mangler det noe...

        List<Beer> beer = beerRepository.getBeer(new BeerFilter());

        return beer;
    }


}
