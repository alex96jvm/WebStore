package dev.alex96jvm.webstore.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class LiquibaseConfig {
    ConfigLoader configLoader;

    public LiquibaseConfig() {
        this.configLoader = new ConfigLoader("application.properties");
    }

    @Bean
    public Liquibase liquibase() throws LiquibaseException, SQLException, ClassNotFoundException {
        Class.forName(configLoader.getProperty("jdbc.driverClassName"));
        String url = configLoader.getProperty("jdbc.url");
        String user = configLoader.getProperty("jdbc.username");
        String password = configLoader.getProperty("jdbc.password");
        String changeLogFile = configLoader.getProperty("liquibase.change-log");
        Connection connection = DriverManager.getConnection(url, user, password);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = new PostgresDatabase();
        database.setConnection(jdbcConnection);
        try (Liquibase liquibase = new Liquibase(changeLogFile,
                new ClassLoaderResourceAccessor(),
                database)) {
            liquibase.update("");
            return liquibase;
        }
    }
}
