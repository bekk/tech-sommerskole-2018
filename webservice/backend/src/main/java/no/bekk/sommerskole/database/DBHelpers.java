package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.Brewery;
import no.bekk.sommerskole.domain.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

class DBHelpers {
    static Beer mapToBeer(ResultSet rs, int rowNum) throws SQLException {
        return new Beer()
                .setId(rs.getInt("beerId"))
                .setName(rs.getString("beerName"))
                .setAbv(rs.getDouble("abv"))
                .setBrewery(mapToBrewery(rs))
                .setCountry(mapToCountry(rs))
                .setCity(rs.getString("cityName"));
    }

    static BeerDetails mapToBeerDetails(ResultSet rs, int rowNum) throws SQLException {
        return new BeerDetails()
                .setId(rs.getInt("beerId"))
                .setName(rs.getString("beerName"))
                .setAbv(rs.getDouble("abv"))
                .setIbu(getInteger("ibu", rs))
                .setKcal(getDouble("kcal", rs))
                .setWebpage(rs.getString("web"))
                .setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime())
                .setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .setBrewery(mapToBrewery(rs))
                .setCountry(mapToCountry(rs))
                .setCity(rs.getString("cityName"));
    }

    static Brewery mapToBrewery(ResultSet rs) throws SQLException {
        if (rs.getString("breweryId") == null) {
            return null;
        }
        return new Brewery()
                .setId(rs.getInt("breweryId"))
                .setName(rs.getString("breweryName"));
    }

    static Brewery mapToBreweries(ResultSet rs, int rowNum) throws SQLException {
        return mapToBrewery(rs);
    }

    static Country mapToCountry(ResultSet rs) throws SQLException {
        if (rs.getString("countryCode") == null) {
            return null;
        }
        return new Country()
                .setCountryCode(rs.getString("countryCode"))
                .setKey(rs.getString("countryKey"))
                .setName(rs.getString("countryName"));
    }

    static Double getDouble(String field, ResultSet rs) throws SQLException {
        double value = rs.getDouble(field);
        return rs.wasNull() ? null : value;
    }

    static Integer getInteger(String field, ResultSet rs) throws SQLException {
        int value = rs.getInt(field);
        return rs.wasNull() ? null : value;
    }
}
