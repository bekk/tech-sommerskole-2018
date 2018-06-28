package no.bekk.sommerskole.filter;


import java.util.ArrayList;
import java.util.List;

public class BeerFilter {
    private float minAbv = 0F;
    private float maxAbv = 100F;

    private List<String> countries = new ArrayList<>();

    private int limit = 10;

    private SortType sortType;
    private Boolean sortDescending;

    public BeerFilter() {
    }

    public SortType getSortType() {
        return sortType;
    }

    public BeerFilter setSortType(SortType sortType) {
        this.sortType = sortType;
        return this;
    }

    public Boolean getSortDescending() {
        return Boolean.TRUE.equals(sortDescending);
    }

    public BeerFilter setSortDescending(Boolean sortDescending) {
        this.sortDescending = sortDescending;
        return this;
    }

    public float getMinAbv() {
        return minAbv;
    }

    public BeerFilter setMinAbv(float minAbv) {
        this.minAbv = minAbv;
        return this;
    }

    public float getMaxAbv() {
        return maxAbv;
    }

    public BeerFilter setMaxAbv(float maxAbv) {
        this.maxAbv = maxAbv;
        return this;
    }

    public List<String> getCountries() {
        return countries;
    }

    public BeerFilter setCountries(List<String> countries) {
        this.countries = countries;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public BeerFilter setLimit(int limit) {
        this.limit = limit;
        return this;
    }
}
