package no.bekk.sommerskole.database;

import ca.krasnay.sqlbuilder.SelectBuilder;
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
        SelectBuilder query = new SelectBuilder()
                .column("brewery.id AS breweryId")
                .column("brewery.title AS breweryName")
                .from("main.breweries AS brewery")
                .orderBy("brewery.title");
        return jdbc.query(query.toString(), DBHelpers::mapToBreweries);
    }
}
