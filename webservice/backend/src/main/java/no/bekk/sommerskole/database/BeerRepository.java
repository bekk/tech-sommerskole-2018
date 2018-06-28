package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class BeerRepository {
    NamedParameterJdbcTemplate jdbc;

    @Inject
    public BeerRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public BeerDetails getBeerDetails(String id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        String query = "SELECT " +
                "beer.id AS beerId, " +
                "beer.title AS beerName, " +
                "beer.abv AS abv, " +
                "beer.kcal AS kcal, " +
                "beer.ibu AS ibu, " +
                "beer.web AS web, " +
                "beer.updated_at AS updated_at, " +
                "beer.created_at AS created_at, " +
                "brewery.id AS breweryId, " +
                "brewery.title AS breweryName, " +
                "country.code AS countryCode, " +
                "country.title AS countryName " +
                "FROM main.beers AS beer " +
                "LEFT OUTER JOIN main.breweries AS brewery ON brewery.id = beer.brewery_id " +
                "LEFT OUTER JOIN main.countries AS country ON beer.country_id = country.id " +
                "WHERE beer.id = :id";

        return jdbc.queryForObject(query, parameterSource, DBHelpers::mapToBeerDetails);
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
                (filter.getSortType() != null ? "ORDER BY " + filter.getSortType().sql + (filter.getSortDescending() ? " DESC " : " ASC ") : "") +
                "LIMIT :limit;";

        return jdbc.query(query, parameterSource, DBHelpers::mapToBeer);
    }
}

