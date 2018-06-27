package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    public List<Beer> getBeer(BeerFilter filter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("minAbv", filter.getMinAbv())
                .addValue("maxAbv", filter.getMaxAbv())
                .addValue("countries", filter.getCountries())
                .addValue("limit", filter.getLimit());

        String query = "SELECT " +
                "beer.id AS beerId, " +
                "beer.title AS beerName, " +
                "beer.abv AS abv, " +
                "brewery.id AS breweryId, " +
                "brewery.title AS breweryName, " +
                "country.code AS countryCode, " +
                "country.title AS countryName " +
                "FROM main.beers AS beer " +
                "LEFT OUTER JOIN main.breweries AS brewery ON brewery.id = beer.brewery_id " +
                "LEFT OUTER JOIN main.countries AS country ON beer.country_id = country.id " +
                "WHERE beer.abv > :minAbv " +
                "AND beer.abv < :maxAbv " +
                (filter.getCountries().size() > 0 ? "AND country.code IN (:countries) " : "") +
                (filter.getSortType() != null ? "ORDER BY " + filter.getSortType().sql + (filter.getSortDescending() ? "DESC " : "ASC ") : "") +
                "LIMIT :limit;";

        return jdbc.query(query, parameterSource, BeerRepository::mapToBeer);
    }

    private static Beer mapToBeer(ResultSet rs, int rowNum) throws SQLException {
        return new Beer()
                .setId(rs.getInt("beerId"))
                .setName(rs.getString("beerName"))
                .setAbv(rs.getFloat("abv"))
                .setBrewery(mapToBrewery(rs))
                .setCountry(mapToCountry(rs));
    }

    private static Brewery mapToBrewery(ResultSet rs) throws SQLException {
        if (rs.getString("breweryId") == null) {
            return null;
        }
        return new Brewery()
                .setId(rs.getInt("breweryId"))
                .setName(rs.getString("breweryName"));
    }

    private static Country mapToCountry(ResultSet rs) throws SQLException {
        if (rs.getString("countryCode") == null) {
            return null;
        }
        return new Country()
                .setCountryCode(rs.getString("countryCode"))
                .setName(rs.getString("countryName"));
    }
}

