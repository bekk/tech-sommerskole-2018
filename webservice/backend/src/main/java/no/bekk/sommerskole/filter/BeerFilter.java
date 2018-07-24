package no.bekk.sommerskole.filter;


import java.util.ArrayList;
import java.util.List;

public class BeerFilter {
    private Float minAbv;
    private Float maxAbv;

    private List<String> countries = new ArrayList<>();

    private int limit = 10;

    private SortType sortType;
    private Boolean sortDescending;

    public BeerFilter() {
    }

    public SortType getSortType() {
        return sortType;
    }

    public BeerFilter withSortType(SortType sortType) {
        this.sortType = sortType;
        return this;
    }

    public Boolean getSortDescending() {
        return Boolean.TRUE.equals(sortDescending);
    }


    public Boolean getSortAscending() {
        return !getSortDescending();
    }

    public BeerFilter withSortDescending(Boolean sortDescending) {
        this.sortDescending = sortDescending;
        return this;
    }

    public Float getMinAbv() {
        return minAbv;
    }

    public BeerFilter withMinAbv(Float minAbv) {
        this.minAbv = minAbv;
        return this;
    }

    public Float getMaxAbv() {
        return maxAbv;
    }

    public BeerFilter withMaxAbv(Float maxAbv) {
        this.maxAbv = maxAbv;
        return this;
    }

    public List<String> getCountries() {
        return countries;
    }

    public BeerFilter withCountries(List<String> countries) {
        this.countries = countries;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public BeerFilter withLimit(int limit) {
        this.limit = limit;
        return this;
    }
}
