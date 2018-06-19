package no.bekk.sommerskole.filter;


import java.util.ArrayList;
import java.util.List;

public class BeerFilter {
    private Boolean indie;
    private float minAbv = 0F;
    private float maxAbv = 100F;

    private List<String> countries = new ArrayList<>();

    private int limit = 10;

    private SortType sortType;
    private Boolean sortDescending;

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public Boolean getSortDescending() {
        return sortDescending == null ? new Boolean(false) : sortDescending;
    }

    public void setSortDescending(Boolean sortDescending) {
        this.sortDescending = sortDescending;
    }


    public BeerFilter() {
    }

    public Boolean getIndie() {
        return indie;
    }

    public BeerFilter setIndie(Boolean indie) {
        this.indie = indie;
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
