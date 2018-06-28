package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.filter.BeerFilter;
import no.bekk.sommerskole.filter.SortType;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerControllerTest {
    @Inject
    private BeerController beerController;

    @Test
    public void shouldReturnAListOfBeers() {
        assertThat(beerController.getBeer(new BeerFilter())).isNotEmpty();
    }

    @Test
    public void shouldLimitNumberOfBeersReturned() {
        BeerFilter filter = new BeerFilter().setLimit(5);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).hasSize(5);
    }

    @Test
    public void shouldFilterBeersByMinAbv() {
        BeerFilter filter = new BeerFilter().setMinAbv(6.0F);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).allMatch(beer -> beer.getAbv() >= 6.0F);
    }

    @Test
    public void shouldFilterBeersByMaxAbv() {
        BeerFilter filter = new BeerFilter().setMaxAbv(6.0F);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).allMatch(beer -> beer.getAbv() <= 6.0F);
    }

    @Test
    public void shouldFilterByCountry() {
        BeerFilter filter = new BeerFilter().setCountries(Arrays.asList("ENG", "CAN"));
        List<Beer> beers = beerController.getBeer(filter);

        assertThat(beers).extracting(beer -> beer.getCountry().getCountryCode()).contains("CAN");
        assertThat(beers).extracting(beer -> beer.getCountry().getCountryCode()).contains("ENG");
        assertThat(beers).allMatch(beer -> filter.getCountries().contains(beer.getCountry().getCountryCode()));
    }

    @Test
    public void shouldSortByAbvAscending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.ABV);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(Beer::getAbv).isSorted();
    }

    @Test
    public void shouldSortByBeerNameAscending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.BEER_NAME);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(Beer::getName).isSorted();
    }

    @Test
    public void shouldSortByCountryAscending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.COUNTRY);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(beer -> beer.getCountry().getName()).isSorted();
    }

    @Test
    public void shouldSortByAbvDescending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.ABV).setSortDescending(true);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(Beer::getAbv).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    public void shouldSortByBeerNameDescending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.BEER_NAME).setSortDescending(true);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(Beer::getName).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    public void shouldSortByBreweryAscending() {
        BeerFilter filter = new BeerFilter().setSortType(SortType.BREWERY_NAME);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).extracting(new BreweryExtractor()).isSorted();
    }

    private class BreweryExtractor implements Extractor<Beer, String> {
        @Override
        public String extract(Beer beer) {
            return beer.getBrewery() == null ? "" : beer.getBrewery().getName();
        }
    }
}