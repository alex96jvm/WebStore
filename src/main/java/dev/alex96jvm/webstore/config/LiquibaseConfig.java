package dev.alex96jvm.webstore.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class LiquibaseConfig {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${liquibase.change-log}")
    private String changeLogFile;

    @Bean
    public Liquibase liquibase() throws LiquibaseException, SQLException, ClassNotFoundException {
        Class.forName(jdbcDriverClassName);
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
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
