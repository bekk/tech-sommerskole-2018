package no.bekk.sommerskole.config;


import no.bekk.sommerskole.database.BeerRepository;
import no.bekk.sommerskole.suggester.Suggester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    Suggester suggester(BeerRepository beerRepository) {
        return new Suggester(beerRepository);
    }
}
