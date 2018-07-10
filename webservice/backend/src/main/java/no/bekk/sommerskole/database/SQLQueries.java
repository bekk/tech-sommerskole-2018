package no.bekk.sommerskole.database;

public class SQLQueries {

    public static final String UPDATE_BEER_DETAILS_QUERY = "UPDATE main.beers SET " +
            "title = :name, " +
            "brewery_id = :breweryId, " +
            "country_id =  (SELECT id FROM main.countries WHERE code = :countryCode), " +
            "ibu = :ibu, " +
            "abv = :abv, " +
            "kcal = :kcal, " +
            "web = :webpage " +
            "WHERE id = :id ";
}
