package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;
import no.bekk.sommerskole.domain.requirements.RequirementsForm;
import no.bekk.sommerskole.suggester.Suggester;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SuggesterControllerTest {

    private static BeerRepository beerRepository = mock(BeerRepository.class);

    private static Suggester suggester = new Suggester(beerRepository);

    private static SuggesterController suggesterController = new SuggesterController(suggester);


    @BeforeClass
    public static void setUp() {
        when(beerRepository.getBeer(any())).thenReturn(testBeers());
    }

    @Test
    public void shouldReturnAtLeastOneBeer() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm().setAbvValue(12.1).setAbvWeight(1.1));
        assertThat(beers).size().isBetween(1, 11);
    }

    @Test
    public void shouldReturn10Beers() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm().setAbvValue(12.1).setAbvWeight(1.1));
        assertThat(beers).size().isEqualTo(10);
    }

    @Test
    public void shouldSortTopAbvFirstIfHighABVTarget() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setAbvValue(100.0)
                .setAbvWeight(1.0));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(6, 11, 7, 10);
    }

    @Test
    public void shouldSortBottomAbVFirstIfLowABVTarget() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setAbvValue(0.0)
                .setAbvWeight(1.0));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(3, 4, 8, 2);
    }

    @Test
    public void shouldSortByCountry() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setCountryValue(asList("FFF", "AAA", "EEE"))
                .setCountryWeight(1.0));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(1, 6, 11, 9);
    }

    @Test
    public void ifLowWeightCountryShouldNotMatter() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setAbvValue(100.0)
                .setAbvWeight(1.0)
                .setCountryValue(asList("FFF"))
                .setCountryWeight(0.00001));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(6, 11, 7, 10);
    }


    @Test
    public void shouldSortByCity() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setCityValue("Chicago")
                .setCityWeight(1.0));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(5, 6, 4, 3);

    }

    @Test
    public void ifLowWeightCityShouldNotMatter() {
        List<Beer> beers = suggesterController.getSuggestion(new RequirementsForm()
                .setCityValue("random")
                .setCityWeight(0.00001)
                .setAbvValue(100.0)
                .setAbvWeight(1.0));
        assertThat(beers.subList(0, 4)).extracting(Beer::getId).contains(6, 11, 7, 10);
    }


    private static List<Beer> testBeers() {
        return asList(
                new Beer(1, "Beer1", new Brewery(1, "Brewery1"), 4.5, new Country("AAA", "1", "aaa", "Continent1", 3), "Cologne"),
                new Beer(2, "Beer2", new Brewery(1, "Brewery1"), 4.0, new Country("BBB", "2", "bbb", "Continent1", 3), "Porto Alegre"),
                new Beer(3, "Beer3", new Brewery(1, "Brewery2"), 3.1, new Country("BBB", "2", "bbb", "Continent1", 3), "Champigo"),
                new Beer(4, "Beer4", new Brewery(1, "Brewery2"), 3.2, new Country("CCC", "3", "ccc", "Continent2", 3), "Chigugo"),
                new Beer(5, "Beer5", new Brewery(1, "Brewery3"), 5.3, new Country("CCC", "3", "ccc", "Continent2", 3), "Chicago"),
                new Beer(6, "Beer6", new Brewery(1, "Brewery3"), 12.1, new Country("AAA", "1", "aaa", "Continent1", 3), "Chacago"),
                new Beer(7, "Beer7", new Brewery(1, "Brewery4"), 8.1, new Country("DDD", "4", "ddd", "Continent3", 1), "Kharkiv"),
                new Beer(8, "Beer8", new Brewery(1, "Brewery5"), 3.7, new Country("BBB", "2", "bbb", "Continent1", 3), "Johannesburg"),
                new Beer(9, "Beer9", new Brewery(1, "Brewery6"), 5.4, new Country("EEE", "5", "eee", "Continent4", 1), "Warsaw"),
                new Beer(10, "Beer10", new Brewery(1, "Brewery7"), 6.7, new Country("CCC", "3", "ccc", "Continent2", 3), "Prague"),
                new Beer(11, "Beer11", new Brewery(1, "Brewery7"), 8.3, new Country("AAA", "1", "aaa", "Continent1", 3), "Busan")

        );
    }


}