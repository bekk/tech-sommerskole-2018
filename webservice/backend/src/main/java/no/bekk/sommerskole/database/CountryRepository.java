package no.bekk.sommerskole.database;

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
        String query = "SELECT countries.code, countries.title, continents.title AS continent, COUNT(beers.id) AS beerCount\n" +
                "FROM countries\n" +
                "LEFT JOIN continents ON countries.continent_id = continents.id\n" +
                "LEFT JOIN beers ON countries.id = beers.country_id\n" +
                "GROUP BY countries.code, countries.title, continents.title\n" +
                "ORDER BY countries.title";
        return jdbc.query(query, CountryRepository::mapToCountry);
    }

    private static Country mapToCountry(ResultSet rs, int rowNum) throws SQLException {
        return new Country()
                .setCountryCode(rs.getString("code"))
                .setName(rs.getString("title"))
                .setContinent(rs.getString("continent"))
                .setNumberOfBeers(rs.getInt("beerCount"));
    }
}
