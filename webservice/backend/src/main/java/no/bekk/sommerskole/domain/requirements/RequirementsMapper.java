package no.bekk.sommerskole.domain.requirements;

import java.util.ArrayList;
import java.util.List;

public class RequirementsMapper {

    public static List<Requirement> mapToRequirements(RequirementsForm form) {
        List<Requirement> requirements = new ArrayList<>();

        if (form.abvWeight != 0 && form.abvWeight != null) {
            requirements.add(new ABVReq(form.abvValue, form.abvWeight));
        }
        if (form.kcalWeight != 0 && form.kcalWeight != null) {
            requirements.add(new KcalReq(form.kcalValue, form.kcalWeight));
        }
        if (form.cityWeight != 0 && form.cityWeight != null) {
            requirements.add(new CityReq(form.cityValue, form.cityWeight));
        }
        if (form.countryWeight != 0 && form.countryWeight != null){
            requirements.add(new CountryReq(form.countryValue, form.countryWeight));
        }

        return requirements;
    }

}
