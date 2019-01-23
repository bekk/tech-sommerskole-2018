package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Brewery;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class BreweryRepository {
    private final NamedParameterJdbcTemplate jdbc;

    @Inject
    public BreweryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Brewery> getBreweries() {
        String query = "select " +
                "brewery.id AS breweryId, " +
                "brewery.title as breweryName " +
                "from main.breweries as brewery " +
                "order by brewery.title";


        return jdbc.query(query, DBHelpers::mapToBreweries);
    }
}
