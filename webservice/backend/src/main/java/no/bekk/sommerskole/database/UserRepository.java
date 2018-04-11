package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class UserRepository {
    NamedParameterJdbcTemplate jdbc;

    @Inject
    public UserRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> getAllUsers() {
        return this.jdbc.query("SELECT * FROM Users;", (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name")));
    }

    public User getUserById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        return this.jdbc.queryForObject(
                "SELECT * FROM Users WHERE id = :id",
                paramSource,
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }

    public void createUser(String name) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name", name);

        this.jdbc.update("INSERT INTO Users (name) VALUES (:name)", paramSource);
    }
}
