package no.bekk.sommerskole.controllers;

import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.domain.Beer;
import no.bekk.sommerskole.domain.BeerFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}