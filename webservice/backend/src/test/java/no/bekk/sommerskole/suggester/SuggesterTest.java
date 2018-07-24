package no.bekk.sommerskole.suggester;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;
import no.bekk.sommerskole.domain.requirements.ABVReq;
import no.bekk.sommerskole.domain.requirements.CityReq;
import no.bekk.sommerskole.domain.requirements.ContinentReq;
import no.bekk.sommerskole.domain.requirements.CountryReq;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuggesterTest {


    private static BeerRepository beerRepository = mock(BeerRepository.class);

    private static Suggester suggester = new Suggester(beerRepository);


    @BeforeClass
    public static void setUp() {
        when(beerRepository.getBeer(any())).thenReturn(testBeers());
    }

    @Test
    public void shouldReturn10Beers() {
        List<Beer> beers = suggester.suggestBeer(singletonList(new ABVReq(12.2, 1.1)));
        assertThat(beers).size().isEqualTo(10);
    }

    @Test
    public void shouldSortTopAbvFirst() {
        List<Beer> beers = suggester.suggestBeer(singletonList(new ABVReq(100.0, 1.0)));
        assertThat(beers).extracting(Beer::getId).contains(6, atIndex(0));
    }

    @Test
    public void shouldSortByContinent() {
        List<Beer> beers = suggester.suggestBeer(singletonList(new ContinentReq("Continent4", 1)));
        assertThat(beers).extracting(Beer::getId).contains(9, atIndex(0));
    }

    @Test
    public void shouldSortByCountry() {
        List<Beer> beers = suggester.suggestBeer(singletonList(new CountryReq(asList("FFF", "AAA", "EEE"), 1)));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(1, 6, 11, 9);
    }

    @Test
    public void shouldSortByCity(){
        List<Beer> beers = suggester.suggestBeer(singletonList(new CityReq("City1ggg", 1)));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(8, 10, 7, 1);

    }

    @Test
    public void shouldSortByKcal(){


    }


    private static List<Beer> testBeers() {
        return asList(
                new Beer(1, "Beer1", new Brewery(1, "Brewery1"), 4.5f, new Country("AAA", "1", "aaa", "Continent1", 3), "City1aa"),
                new Beer(2, "Beer2", new Brewery(1, "Brewery1"), 4.0f, new Country("BBB", "2", "bbb", "Continent1", 3), "City2bb"),
                new Beer(3, "Beer3", new Brewery(1, "Brewery2"), 3.1f, new Country("BBB", "2", "bbb", "Continent1", 3), "City3cc"),
                new Beer(4, "Beer4", new Brewery(1, "Brewery2"), 3.2f, new Country("CCC", "3", "ccc", "Continent2", 3), "City4dd"),
                new Beer(5, "Beer5", new Brewery(1, "Brewery3"), 5.3f, new Country("CCC", "3", "ccc", "Continent2", 3), "City4ee"),
                new Beer(6, "Beer6", new Brewery(1, "Brewery3"), 12.1f, new Country("AAA", "1", "aaa", "Continent1", 3), "City5ff"),
                new Beer(7, "Beer7", new Brewery(1, "Brewery4"), 8.1f, new Country("DDD", "4", "ddd", "Continent3", 1), "City1gh"),
                new Beer(8, "Beer8", new Brewery(1, "Brewery5"), 3.7f, new Country("BBB", "2", "bbb", "Continent1", 3), "City1ggg"),
                new Beer(9, "Beer9", new Brewery(1, "Brewery6"), 5.4f, new Country("EEE", "5", "eee", "Continent4", 1), "City6aa"),
                new Beer(10, "Beer10", new Brewery(1, "Brewery7"), 6.7f, new Country("CCC", "3", "ccc", "Continent2", 3), "City1gg"),
                new Beer(11, "Beer11", new Brewery(1, "Brewery7"), 8.3f, new Country("AAA", "1", "aaa", "Continent1", 3), "City2hh")

        );
    }

}