package no.bekk.sommerskole.database;

import ca.krasnay.sqlbuilder.SelectBuilder;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.BeerDetailsForm;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static no.bekk.sommerskole.database.SQLQueries.UPDATE_BEER_DETAILS_QUERY;


@Repository
public class BeerRepository {

    NamedParameterJdbcTemplate jdbc;

    @Inject
    public BeerRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public BeerDetails getBeerDetails(String id) {
        return null;
    }

    public List<Beer> getBeer(BeerFilter filter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("limit", filter.getLimit());

        SelectBuilder selectBuilder = new SelectBuilder()
                .column("beer.id AS beerId")
                .column("beer.title AS beerName")
                .column("beer.abv AS abv")
                .column("brewery.id AS breweryId")
                .column("brewery.title AS breweryName")
                .column("country.code AS countryCode")
                .column("country.title AS countryName")
                .column("country.key AS countryKey")
                .column("city.title AS cityName")
                .from("main.beers AS beer")
                .leftJoin("main.breweries AS brewery ON brewery.id = beer.brewery_id")
                .leftJoin("main.countries AS country ON beer.country_id = country.id")
                .leftJoin("main.cities AS city ON beer.city_id = city.id");

        String query = selectBuilder.toString() + " LIMIT :limit";
        return jdbc.query(query, parameterSource, DBHelpers::mapToBeer);
    }

    public void setBeerDetails(BeerDetailsForm details) {

    }


}

