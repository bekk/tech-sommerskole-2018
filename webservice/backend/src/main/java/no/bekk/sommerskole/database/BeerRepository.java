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
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        String query = new SelectBuilder()
                .column("beer.id AS beerId")
                .column("beer.title AS beerName")
                .column("beer.abv AS abv")
                .column("beer.kcal AS kcal")
                .column("beer.ibu AS ibu")
                .column("beer.web AS web")
                .column("beer.updated_at AS updated_at")
                .column("beer.created_at AS created_at")
                .column("brewery.id AS breweryId")
                .column("brewery.title AS breweryName")
                .column("country.code AS countryCode")
                .column("country.title AS countryName")
                .column("country.key AS countryKey")
                .from("main.beers AS beer")
                .leftJoin("main.breweries AS brewery ON brewery.id = beer.brewery_id")
                .leftJoin("main.countries As country ON beer.country_id = country.id")
                .where("beer.id = :id")
                .toString();

        return jdbc.queryForObject(query, parameterSource, DBHelpers::mapToBeerDetails);
    }

    public List<Beer> getBeer(BeerFilter filter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("minAbv", filter.getMinAbv())
                .addValue("maxAbv", filter.getMaxAbv())
                .addValue("countries", filter.getCountries())
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
                .from("main.beers AS beer")
                .leftJoin("main.breweries AS brewery ON brewery.id = beer.brewery_id")
                .leftJoin("main.countries As country ON beer.country_id = country.id");

        if (filter.getMaxAbv() != null) {
            selectBuilder.where("beer.abv <= :maxAbv");
        }

        if (filter.getMinAbv() != null) {
            selectBuilder.where("beer.abv >= :minAbv");
        }

        if (filter.getCountries().size() > 0) {
            selectBuilder.where("country.code IN (:countries)");
        }

        if (filter.getSortType() != null) {
            selectBuilder.orderBy(filter.getSortType().sql, filter.getSortAscending());
        }

        String query = selectBuilder.toString() + " LIMIT :limit";
        return jdbc.query(query, parameterSource, DBHelpers::mapToBeer);
    }

    public void setBeerDetails(BeerDetailsForm details) {
        MapSqlParameterSource parameterSource = beerDetailsParamSource(details);

        String query = UPDATE_BEER_DETAILS_QUERY;

        jdbc.update(query, parameterSource);
    }

    private MapSqlParameterSource beerDetailsParamSource(BeerDetailsForm details) {
        return new MapSqlParameterSource()
                    .addValue("id", details.getId())
                    .addValue("name", details.getName())
                    .addValue("breweryId", details.getBrewery())
                    .addValue("countryCode", details.getCountry())
                    .addValue("ibu", details.getIbu())
                    .addValue("abv", details.getAbv())
                    .addValue("kcal", details.getKcal())
                    .addValue("webpage", details.getWebpage());
    }
}

