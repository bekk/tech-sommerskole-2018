package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BeerRepository {
    NamedParameterJdbcTemplate jdbc;

    @Inject
    public BeerRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Beer> getBeer() {
        return jdbc.query(
                "SELECT " +
                        "beer.id AS beerId, " +
                        "beer.title AS beerName, " +
                        "beer.abv AS abv, " +
                        "brewery.id AS breweryId, " +
                        "brewery.title AS breweryName, " +
                        "brewery.indie AS indie, " +
                        "country.code AS countryCode, " +
                        "country.title AS countryName " +
                        "FROM main.beers AS beer " +
                        "JOIN main.breweries AS brewery ON brewery.id = beer.brewery_id " +
                        "JOIN main.countries AS country ON brewery.country_id = country.id " +
                        "LIMIT 10;",
               BeerRepository::mapToBeer
        );
    }

    private static Beer mapToBeer(ResultSet rs, int rowNum) throws SQLException {
        return new Beer()
                .setId(rs.getInt("beerId"))
                .setName(rs.getString("beerName"))
                .setAbv(rs.getFloat("abv"))
                .setBrewery(new Brewery()
                        .setId(rs.getInt("breweryId"))
                        .setName(rs.getString("breweryName"))
                        .setIndie(rs.getBoolean("indie"))
                        .setCountry(new Country()
                                .setCountryCode(rs.getString("countryCode"))
                                .setName(rs.getString("countryName"))
                        )
                );
    }
}
