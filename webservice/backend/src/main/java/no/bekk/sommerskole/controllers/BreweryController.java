package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BreweryRepository;
import no.bekk.sommerskole.domain.Brewery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/brewery")
public class BreweryController {

    private BreweryRepository repository;

    @Inject
    BreweryController(BreweryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Brewery> getBreweries() {
        return repository.getBreweries();
    }
}
