package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerDetails;
import no.bekk.sommerskole.filter.BeerFilter;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/beer")
public class BeerController {
    private BeerRepository beerRepository;

    @Inject
    public BeerController(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @GetMapping
    public List<Beer> getBeer(@ModelAttribute BeerFilter beerFilter) {
        return beerRepository.getBeer(beerFilter);
    }

    @GetMapping("/{id}")
    public BeerDetails getBeerDetails(@PathVariable("id") String id) {
        return beerRepository.getBeerDetails(id);
    }
}

