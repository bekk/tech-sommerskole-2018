package no.bekk.sommerskole.database;

import ca.krasnay.sqlbuilder.SelectBuilder;
import no.bekk.sommerskole.domain.Country;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryRepository {
    private final NamedParameterJdbcTemplate jdbc;

    @Inject
    public CountryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Country> getCountries() {
        String query = new SelectBuilder()
                .column("countries.code")
                .column("countries.title")
                .column("countries.key")
                .column("continents.title AS continent")
                .column("COUNT(beers.id) AS beerCount")
                .from("countries")
                .leftJoin("continents ON countries.continent_id = continents.id")
                .leftJoin("beers ON countries.id = beers.country_id")
                .groupBy("countries.code")
                .groupBy("countries.title")
                .groupBy("countries.title")
                .orderBy("countries.title")
                .toString();

        return jdbc.query(query, CountryRepository::mapToCountry);
    }

    private static Country mapToCountry(ResultSet rs, int rowNum) throws SQLException {
        return new Country()
                .setCountryCode(rs.getString("code"))
                .setKey(rs.getString("key"))
                .setName(rs.getString("title"))
                .setContinent(rs.getString("continent"))
                .setNumberOfBeers(rs.getInt("beerCount"));
    }
}
