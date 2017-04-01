package ru.ssau.twister.dao;

import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.UserDto;
import ru.ssau.twister.services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDao {
    private final DatabaseService databaseService;

    public UserDao() {
        databaseService = DatabaseService.getInstance();
    }

    public Optional<User> findUserById(Long id) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT u.user_name, u.user_avatar FROM users u WHERE u.user_id = ?"
            );

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();

                user.setId(id);
                user.setUsername(resultSet.getString(1));
                user.setAvatar(resultSet.getBytes(2));

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve user from USERS table", e);
        }
    }

    public Optional<User> findUserCredentialsByName(String name) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT u.user_id, u.user_name, u.user_pass FROM users u WHERE u.user_name = ?"
            );

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve user from USERS table", e);
        }
    }

    public Optional<User> findUserFullGraphByName(String name, User me) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT u.user_id, u.user_name, u.user_avatar, " +
                            "count(CASE WHEN f.followee_id = u.user_id THEN 1 END), " +
                            "count(CASE WHEN f.follower_id = u.user_id THEN 1 END), " +
                            "bool_or(f.followee_id = ?), bool_or(f.follower_id = ?) " +
                        "FROM users u INNER JOIN follows f " +
                            "ON (f.followee_id = u.user_id OR f.follower_id = u.user_id) " +
                        "WHERE u.user_name = ? " +
                        "GROUP BY u.user_id, u.user_name, u.user_avatar"
            );

            statement.setLong(1, me.getId());
            statement.setLong(2, me.getId());
            statement.setString(3, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserDto user = new UserDto();

                user.setId(resultSet.getLong(1));
                user.setUsername(resultSet.getString(2));
                user.setAvatar(resultSet.getBytes(3));
                user.setFollowersCount(resultSet.getLong(4));
                user.setFolloweesCount(resultSet.getLong(5));
                user.setFollowingMe(resultSet.getBoolean(6));
                user.setFollowingByMe(resultSet.getBoolean(7));

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve user from USERS table", e);
        }
    }

    public Optional<User> saveUser(String username, String password) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "INSERT INTO users (user_name, user_pass) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, username);
            statement.setString(2, password);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                User user = new User();

                user.setId(generatedKeys.getLong(1));
                user.setUsername(username);
                user.setPassword(password);

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save user in USERS table", e);
        }
    }

    public void updateUser(User user) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "UPDATE users SET user_name = ?, user_pass = ?, user_avatar = ? WHERE user_id = ?"
            );

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBytes(3, user.getAvatar());
            statement.setLong(4, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update user in USERS table", e);
        }
    }

}
