package ru.ssau.twister.dao;

import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private static final String FIND_POSTS_BY_USER_QUERY = "SELECT p.* FROM posts p " +
            "INNER JOIN users u ON p.post_user_id = u.user_id WHERE u.user_id = ?";

    private final DatabaseService databaseService;

    private PreparedStatement findAllPostsByUser;

    public PostDao() {
        databaseService = DatabaseService.getInstance();
    }

    public List<Post> findAllPostsByUser(User user) {
        if (findAllPostsByUser == null) {
            findAllPostsByUser = databaseService.prepareStatement(FIND_POSTS_BY_USER_QUERY);
        }

        try {
            findAllPostsByUser.setLong(1, user.getId());

            ResultSet resultSet = findAllPostsByUser.executeQuery();
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                Post post = new Post();

                post.setId(resultSet.getLong("post_id"));
                post.setUser(user);
                post.setText(resultSet.getString("post_text"));
                post.setDate(resultSet.getDate("post_date"));

                posts.add(post);
            }

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve Posts from POSTS table", e);
        }
    }
}
