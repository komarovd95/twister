package ru.ssau.twister.dao;

import ru.ssau.twister.domain.User;
import ru.ssau.twister.services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
    private static final String FIND_BY_USERNAME_QUERY = "SELECT u.* FROM users u WHERE u.user_name = ?";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (user_name, user_pass, user_role) VALUES (?, ?, ?)";

    private final DatabaseService databaseService;

    private PreparedStatement findByUsernameStatement;
    private PreparedStatement saveUser;

    public UserDao() {
        databaseService = DatabaseService.getInstance();
    }

    public Optional<User> findByUsername(String username) {
        if (findByUsernameStatement == null) {
            findByUsernameStatement = databaseService.prepareStatement(FIND_BY_USERNAME_QUERY);
        }

        try {
            findByUsernameStatement.setString(1, username);

            ResultSet resultSet = findByUsernameStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("user_pass"));
                user.setRole(User.UserRole.valueOf(resultSet.getString("user_role")));
                user.setAvatar(resultSet.getBytes("user_avatar"));

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve User from USERS table", e);
        }
    }

    public Optional<User> saveUser(String username, String password) {
        if (saveUser == null) {
            saveUser = databaseService.prepareStatement(SAVE_USER_QUERY);
        }

        databaseService.withTransaction(c -> {
            try {
                saveUser.setString(1, username);
                saveUser.setString(2, password);
                saveUser.setString(3, User.UserRole.USER.toString());

                saveUser.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot insert values into USERS table", e);
            }
        });

        return findByUsername(username);
    }

}
