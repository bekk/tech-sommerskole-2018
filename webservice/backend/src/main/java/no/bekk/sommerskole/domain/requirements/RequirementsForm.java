package no.bekk.sommerskole.domain.requirements;

import java.util.List;

public class RequirementsForm {

    private Double abvWeight;
    private Double abvValue;
    private Double kcalWeight;
    private Double kcalValue;
    private Double cityWeight;
    private String cityValue;
    private Double countryWeight;
    private List<String> countryValue;


    Double getAbvWeight() {
        return abvWeight;
    }

    public RequirementsForm setAbvWeight(Double abvWeight) {
        this.abvWeight = abvWeight;
        return this;
    }

    Double getAbvValue() {
        return abvValue;
    }

    public RequirementsForm setAbvValue(Double abvValue) {
        this.abvValue = abvValue;
        return this;
    }

    Double getKcalWeight() {
        return kcalWeight;
    }

    public RequirementsForm setKcalWeight(Double kcalWeight) {
        this.kcalWeight = kcalWeight;
        return this;
    }

    Double getKcalValue() {
        return kcalValue;
    }

    public RequirementsForm setKcalValue(Double kcalValue) {
        this.kcalValue = kcalValue;
        return this;
    }

    Double getCityWeight() {
        return cityWeight;
    }

    public RequirementsForm setCityWeight(Double cityWeight) {
        this.cityWeight = cityWeight;
        return this;
    }

    String getCityValue() {
        return cityValue;
    }

    public RequirementsForm setCityValue(String cityValue) {
        this.cityValue = cityValue;
        return this;
    }

    Double getCountryWeight() {
        return countryWeight;
    }

    public RequirementsForm setCountryWeight(Double countryWeight) {
        this.countryWeight = countryWeight;
        return this;
    }

    List<String> getCountryValue() {
        return countryValue;
    }

    public RequirementsForm setCountryValue(List<String> countryValue) {
        this.countryValue = countryValue;
        return this;
    }
}
