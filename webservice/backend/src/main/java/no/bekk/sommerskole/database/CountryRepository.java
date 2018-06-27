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
        String query = "SELECT countries.*, continents.title AS continent " +
                "FROM countries " +
                "LEFT JOIN continents ON countries.continent_id = continents.id " +
                "ORDER BY countries.title ASC";
        return jdbc.query(query, CountryRepository::mapToCountry);
    }

    private static Country mapToCountry(ResultSet rs, int rowNum) throws SQLException {
        return new Country()
                .setCountryCode(rs.getString("code"))
                .setName(rs.getString("title"))
                .setContinent(rs.getString("continent"));
    }
}
