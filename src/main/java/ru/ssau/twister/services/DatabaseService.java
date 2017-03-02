package ru.ssau.twister.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class DatabaseService {
    private Connection connection;

    private DatabaseService() {
        initialize();
    }

    private void initialize() {
        try (InputStream inputStream = Files.newInputStream(Paths.get(DatabaseService.class.getClassLoader()
                        .getResource("data_source.properties").toURI()), StandardOpenOption.READ)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, Objects.requireNonNull(user),
                    Objects.requireNonNull(password));
            connection.setAutoCommit(false);
        } catch (IOException | SQLException | URISyntaxException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot instantiate DatabaseService", e);
        }
    }

    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while preparing statement", e);
        }
    }

    public void withTransaction(Consumer<Connection> connectionConsumer) {
        try {
            connectionConsumer.accept(Objects.requireNonNull(connection));
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException("Exception occurred while rollbacking transaction", e1);
            }

            throw new RuntimeException("Transaction has been rollbacked", e);
        }
    }

    public void cleanUp() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Exception occurred while cleaning up service environment", e);
            }
        }
    }

    private static class ServiceHolder {
        private static final DatabaseService INSTANCE = new DatabaseService();
    }

    public static DatabaseService getInstance() {
        return ServiceHolder.INSTANCE;
    }
}
