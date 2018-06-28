package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.filter.BeerFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.Arrays;
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
        BeerFilter filter = new BeerFilter().setCountries(Arrays.asList("ENG", "NOR"));
        List<Beer> beers = beerController.getBeer(filter);

        assertThat(beers).extracting(beer -> beer.getCountry().getCountryCode()).contains("ENG");
        assertThat(beers).extracting(beer -> beer.getCountry().getCountryCode()).contains("NOR");
        assertThat(beers).allMatch(beer -> filter.getCountries().contains(beer.getCountry().getCountryCode()));
    }
}