package no.bekk.sommerskole.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {
    @Bean
    public NamedParameterJdbcTemplate jdbc(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
}
