package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.CountryRepository;
import no.bekk.sommerskole.domain.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    private CountryRepository countryRepository;

    @Inject
    public CountryController(CountryRepository countryRepository) {

        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> getCountries() {
        return countryRepository.getCountries();
    }

}
