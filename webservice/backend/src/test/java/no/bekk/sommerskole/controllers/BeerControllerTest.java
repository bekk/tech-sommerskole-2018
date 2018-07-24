package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.BeerDetailsForm;
import no.bekk.sommerskole.filter.BeerFilter;
import no.bekk.sommerskole.filter.SortType;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
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
        BeerFilter filter = new BeerFilter().setMinAbv(6.0);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).allMatch(beer -> beer.getAbv() >= 6.0);
    }

    @Test
    public void shouldFilterBeersByMaxAbv() {
        BeerFilter filter = new BeerFilter().setMaxAbv(6.0);
        List<Beer> beers = beerController.getBeer(filter);
        assertThat(beers).allMatch(beer -> beer.getAbv() <= 6.0);
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
        assertThat(beers).extracting(new BreweryNameExtractor()).isSorted();
    }

    @Test
    public void postShouldUpdateAllValues() throws IOException {
        BeerDetailsForm details = createBeerDetailsForm();

        beerController.postBeerDetails(details, new MockHttpServletResponse());
        BeerDetails res = beerController.getBeerDetails("371");

        assertThat(res.getId()).isEqualTo(details.getId());
        assertThat(res.getName()).isEqualTo(details.getName());
        assertThat(res.getBrewery().getId()).isEqualTo(details.getBrewery());
        assertThat(res.getCountry().getCountryCode()).isEqualTo(details.getCountry());
        assertThat(res.getIbu()).isEqualTo(details.getIbu());
        assertThat(res.getAbv()).isEqualTo(details.getAbv());
        assertThat(res.getKcal()).isEqualTo(details.getKcal());

    }


    @Test
    public void postShouldNotEditDatabase() {
        BeerDetailsForm details = createBeerDetailsForm();

        BeerDetails res = beerController.getBeerDetails("371");

        assertThat(res.getId()).isEqualTo(details.getId());
        assertThat(res.getName()).isNotEqualTo(details.getName());
        assertThat(getSafeBreweryId(res)).isNotEqualTo(details.getBrewery());
        assertThat(getSafeCountryCode(res)).isNotEqualTo(details.getCountry());
        assertThat(res.getIbu()).isNotEqualTo(details.getIbu());
        assertThat(res.getAbv()).isNotEqualTo(details.getAbv());
        assertThat(res.getKcal()).isNotEqualTo(details.getKcal());

    }

    private BeerDetailsForm createBeerDetailsForm() {
        return new BeerDetailsForm(
                371,
                "myBeer",
                37,
                "NOR",
                123,
                12.3,
                3.21,
                "url");
    }

    private class BreweryNameExtractor implements Extractor<Beer, String> {
        @Override
        public String extract(Beer beer) {
            return beer.getBrewery() == null ? "" : beer.getBrewery().getName();
        }
    }

    private int getSafeBreweryId(BeerDetails beer) {
        return beer.getBrewery() == null ? 0 : beer.getBrewery().getId();
    }

    private String getSafeCountryCode(BeerDetails beer) {
        return beer.getCountry() == null ? "" : beer.getCountry().getCountryCode();
    }

}