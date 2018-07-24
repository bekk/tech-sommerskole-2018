package no.bekk.sommerskole.domain.requirements;

import java.util.ArrayList;
import java.util.List;

public class RequirementsMapper {

    public static List<Requirement> mapToRequirements(RequirementsForm form) {
        List<Requirement> requirements = new ArrayList<>();

        if (form.getAbvWeight() != null && form.getAbvWeight() != 0) {
            requirements.add(new ABVReq(form.getAbvValue(), form.getAbvWeight()));
        }
        if (form.getKcalWeight() != null && form.getKcalWeight() != 0) {
            requirements.add(new KcalReq(form.getKcalValue(), form.getKcalWeight()));
        }
        if (form.getCityWeight() != null && form.getCityWeight() != 0) {
            requirements.add(new CityReq(form.getCityValue(), form.getCityWeight()));
        }
        if (form.getCountryWeight() != null && form.getCountryWeight() != 0){
            requirements.add(new CountryReq(form.getCountryValue(), form.getCountryWeight()));
        }

        return requirements;
    }

}
