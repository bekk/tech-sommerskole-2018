package no.bekk.sommerskole.filter;

public enum SortType {
    ABV("beer.abv"),
    BEER_NAME("beer.title"),
    BREWERY_NAME("brewery.name"),
    COUNTRY("country.title");

    public String sql;

    SortType(String sql) {
        this.sql = sql;
    }

}