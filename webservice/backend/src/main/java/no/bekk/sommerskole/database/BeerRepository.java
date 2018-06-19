package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.Beer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BeerRepository {
    NamedParameterJdbcTemplate jdbc;

    @Inject
    public BeerRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Beer> getBeer() {
        return jdbc.query(
                "SELECT * FROM main.beers;",
               BeerRepository::mapToBeer
        );
    }

    private static Beer mapToBeer(ResultSet rs, int rowNum) throws SQLException {
        return new Beer(
                rs.getInt("id"),
                rs.getString("title"),
                null,
                rs.getFloat("abv")
        );
    }
}
