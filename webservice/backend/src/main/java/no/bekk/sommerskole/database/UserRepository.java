package no.bekk.sommerskole.database;

import no.bekk.sommerskole.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> getAllUsers() {
        return this.jdbc.query("SELECT * FROM Users;", (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name")));
    }

    public User getUserById(int id) {
        return this.jdbc.queryForObject(
                "SELECT * FROM Users WHERE id = ?",
                new Object[] { id },
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
        );
    }

    public void createUser(String name) {
        this.jdbc.update("INSERT INTO Users (name) VALUES (?)", new Object[] { name });
    }
}
