package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BreweryRepository;
import no.bekk.sommerskole.domain.Brewery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BreweryControllerTest {
    @Inject
    private BreweryController controller;

    @Test
    public void shouldReturnAListOfBreweries() {
        assertThat(controller.getBreweries()).isNotEmpty();
    }

    @Test
    public void shouldReturnASortedListOfBreweries() {
        List<Brewery> breweries = controller.getBreweries();
        assertThat(breweries).extracting(Brewery::getName).isSorted();
    }

    @Test
    public void shouldPopulateAllFields() {
        NamedParameterJdbcTemplate jdbc = mock(NamedParameterJdbcTemplate.class);
        BreweryRepository repo = new BreweryRepository(jdbc);
        BreweryController controller = new BreweryController(repo);

        //when(jdbc.query(any(),any())).thenReturn()


    }
}
