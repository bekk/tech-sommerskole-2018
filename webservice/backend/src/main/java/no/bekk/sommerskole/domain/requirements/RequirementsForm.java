package no.bekk.sommerskole.domain.requirements;

import java.util.List;

public class RequirementsForm {

    public Double abvWeight;
    public Double abvValue;
    public Double kcalWeight;
    public Double kcalValue;
    public Double cityWeight;
    public String cityValue;
    public Double countryWeight;
    public List<String> countryValue;


    public Double getAbvWeight() {
        return abvWeight;
    }

    public void setAbvWeight(Double abvWeight) {
        this.abvWeight = abvWeight;
    }

    public Double getAbvValue() {
        return abvValue;
    }

    public void setAbvValue(Double abvValue) {
        this.abvValue = abvValue;
    }

    public Double getKcalWeight() {
        return kcalWeight;
    }

    public void setKcalWeight(Double kcalWeight) {
        this.kcalWeight = kcalWeight;
    }

    public Double getKcalValue() {
        return kcalValue;
    }

    public void setKcalValue(Double kcalValue) {
        this.kcalValue = kcalValue;
    }

    public Double getCityWeight() {
        return cityWeight;
    }

    public void setCityWeight(Double cityWeight) {
        this.cityWeight = cityWeight;
    }

    public String getCityValue() {
        return cityValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    public Double getCountryWeight() {
        return countryWeight;
    }

    public void setCountryWeight(Double countryWeight) {
        this.countryWeight = countryWeight;
    }

    public List<String> getCountryValue() {
        return countryValue;
    }

    public void setCountryValue(List<String> countryValue) {
        this.countryValue = countryValue;
    }
}
