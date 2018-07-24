package no.bekk.sommerskole.suggester;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.requirements.Requirement;
import no.bekk.sommerskole.filter.BeerFilter;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toList;

public class Suggester {

    private BeerRepository beerRepository;

    @Inject
    public Suggester(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public List<Beer> suggestBeer(List<Requirement> requirements) {

        List<Beer> beers = beerRepository.getBeer(new BeerFilter().withLimit(9999));
        return findTop10(requirements, beers);


    }

    private List<Beer> findTop10(List<Requirement> requirements, List<Beer> beers) {
        Map<Beer, Double> beerWorth = beers.stream().collect(Collectors.toMap(beer -> beer, beer -> worthOfBeer(requirements, beer)));

        return beerWorth.entrySet().stream()
                .sorted(reverseOrder(comparingByValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(toList());


    }

    private Double worthOfBeer(List<Requirement> requirements, Beer beer) {
        return requirements.stream().map(req -> req.calculateCost(beer)).reduce(0.0, Double::sum);
    }
}
