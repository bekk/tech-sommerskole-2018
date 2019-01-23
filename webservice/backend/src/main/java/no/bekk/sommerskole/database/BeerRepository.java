package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.BeerDetailsForm;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
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
                "beer.id as beerId, " +
                "beer.title as beerName, " +
                "beer.abv as abv, " +
                "beer.kcal as kcal, " +
                "beer.ibu as ibu, " +
                "beer.web as web, " +
                "beer.updated_at as updated_at, " +
                "beer.created_at as created_at, " +
                "brewery.id as breweryId, " +
                "brewery.title as breweryName, " +
                "country.code as countryCode, " +
                "country.title as countryName, " +
                "country.key as countryKey, " +
                "city.title as cityName " +
                "from main.beers as beer " +
                "left join main.breweries as brewery on brewery.id = beer.brewery_id " +
                "left join main.countries as country on country.id = beer.country_id " +
                "left join main.cities as city on city.id = beer.city_id " +
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
                "beer.title as beerName, " +
                "beer.id as beerId, " +
                "beer.abv as abv, " +
                "brewery.id as breweryId, " +
                "brewery.title as breweryName, " +
                "country.code as countryCode, " +
                "country.title as countryName, " +
                "country.key as countryKey, " +
                "city.title as cityName " +
                "from main.beers as beer " +
                "left join main.breweries as brewery on brewery.id = beer.brewery_id " +
                "left join main.countries as country on country.id = beer.country_id " +
                "left join main.cities as city on beer.city_id = city.id";

        query += " LIMIT :limit;";
        return jdbc.query(query, parameterSource, DBHelpers::mapToBeer);
    }

    public void setBeerDetails(BeerDetailsForm details) {
        MapSqlParameterSource parameterSource = beerDetailsParamSource(details);

        String query = "";

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

