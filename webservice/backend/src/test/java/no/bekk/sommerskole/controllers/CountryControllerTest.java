package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.domain.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryControllerTest {
    @Inject
    private CountryController countryController;

    @Test
    public void shouldReturnAListOfCountries() {
        assertThat(countryController.getCountries()).isNotEmpty();
    }

    @Test
    public void shouldReturnASortedListOfCountries() {
        List<Country> countries = countryController.getCountries();
        assertThat(countries).extracting(Country::getName).isSorted();
    }
}
