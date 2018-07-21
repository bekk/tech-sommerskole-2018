package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.domain.BeerDetailsForm;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/beer")
public class BeerController {
    private BeerRepository beerRepository;

    @Inject
    public BeerController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public List<Beer> getBeer(@ModelAttribute BeerFilter beerFilter) {
        return beerRepository.getBeer(beerFilter);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{id}")
    public BeerDetails getBeerDetails(@PathVariable("id") String id) {
        return beerRepository.getBeerDetails(id);
    }

    @PostMapping
    public void postBeerDetails(@ModelAttribute BeerDetailsForm beerDetailsForm, HttpServletResponse response) throws IOException {
        beerRepository.setBeerDetails(beerDetailsForm);
        response.sendRedirect("/details.html?id=" + beerDetailsForm.getId());
    }
}

