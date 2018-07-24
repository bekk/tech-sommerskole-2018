package no.bekk.sommerskole.controllers;


import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.requirements.Requirement;
import no.bekk.sommerskole.domain.requirements.RequirementsForm;
import no.bekk.sommerskole.domain.requirements.RequirementsMapper;
import no.bekk.sommerskole.suggester.Suggester;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/suggester")
public class SuggesterController {


    private Suggester suggester;

    @Inject
    public SuggesterController(Suggester suggester) {
        this.suggester = suggester;
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public List<Beer> getSuggestion(@ModelAttribute RequirementsForm form) {
        List<Requirement> requirements = RequirementsMapper.mapToRequirements(form);
        return suggester.suggestBeer(requirements);
    }

}
