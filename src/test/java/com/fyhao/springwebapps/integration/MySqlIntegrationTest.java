package com.fyhao.springwebapps.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "MYSQL_HOST", matches = ".+")
class MySqlIntegrationTest {

    @Test
    void writesAndReadsMySqlData() throws Exception {
        String host = System.getenv("MYSQL_HOST");
        String port = environmentOrDefault("MYSQL_PORT", "3306");
        String database = environmentOrDefault("MYSQL_DATABASE", "spring_scripting");
        String user = environmentOrDefault("MYSQL_USER", "root");
        String password = environmentOrDefault("MYSQL_PASSWORD", "root");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS scripting_test");
            statement.execute("CREATE TABLE scripting_test (id INT PRIMARY KEY, message VARCHAR(100) NOT NULL)");
            try {
                try (PreparedStatement insert = connection.prepareStatement(
                        "INSERT INTO scripting_test (id, message) VALUES (?, ?)")) {
                    insert.setInt(1, 1);
                    insert.setString(2, "connected to MySQL");
                    assertThat(insert.executeUpdate()).isEqualTo(1);
                }

                try (ResultSet result = statement.executeQuery(
                        "SELECT message FROM scripting_test WHERE id = 1")) {
                    assertThat(result.next()).isTrue();
                    assertThat(result.getString("message")).isEqualTo("connected to MySQL");
                }
            } finally {
                statement.execute("DROP TABLE scripting_test");
            }
        }
    }

    private String environmentOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
