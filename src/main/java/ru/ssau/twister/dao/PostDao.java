package ru.ssau.twister.dao;

import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.PostDto;
import ru.ssau.twister.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private static final String FIND_POSTS_BY_USER_QUERY = "SELECT p.* FROM posts p " +
            "INNER JOIN users u ON p.post_user_id = u.user_id WHERE u.user_id = ? ORDER BY p.post_date DESC";
    private static final String SAVE_POST_QUERY = "INSERT INTO posts (post_text, post_date, post_user_id) VALUES(?, ?, ?)";
    private static final String FIND_POST_BY_ID = "SELECT p.* FROM posts p WHERE p.post_id = ?";


    private final DatabaseService databaseService;

    private PreparedStatement savePost;
    private PreparedStatement findPostById;

    public PostDao() {
        databaseService = DatabaseService.getInstance();
    }

    public List<Post> findAllPostsByUser(User user, User me) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT p.post_id, p.post_text, p.post_date, u.user_name, " +
                            "count(c.*), count(pl.*), bool_or(pl.user_id = ?) " +
                            "FROM posts p " +
                            "INNER JOIN users u ON p.post_user_id = u.user_id " +
                            "LEFT OUTER JOIN comments c ON c.comment_post_id = p.post_id " +
                            "LEFT OUTER JOIN posts_likes pl ON pl.post_id = p.post_id " +
                            "WHERE p.post_user_id = ? " +
                            "GROUP BY p.post_id, p.post_text, p.post_date, u.user_name " +
                            "ORDER BY p.post_date DESC"
            );

            statement.setLong(1, me.getId());
            statement.setLong(2, user.getId());

            ResultSet resultSet = statement.executeQuery();
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                PostDto post = new PostDto();
                post.setId(resultSet.getLong(1));
                post.setText(resultSet.getString(2));
                post.setDate(new java.util.Date(resultSet.getTimestamp(3).getTime()));

                User u = new User();
                u.setUsername(resultSet.getString(4));

                post.setUser(u);

                post.setCommentsCount(resultSet.getLong(5));
                post.setLikesCount(resultSet.getLong(6));
                post.setLikedByMe(resultSet.getBoolean(7));

                posts.add(post);
            }

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve post from POSTS table", e);
        }
    }

    public Optional<Post> findPostById(Long postId, User me) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT p.post_id, p.post_text, p.post_date, u.user_name, " +
                            "count(c.*), count(pl.*), bool_or(pl.user_id = ?) " +
                            "FROM posts p " +
                            "INNER JOIN users u ON p.post_user_id = u.user_id " +
                            "LEFT OUTER JOIN comments c ON c.comment_post_id = p.post_id " +
                            "LEFT OUTER JOIN posts_likes pl ON pl.post_id = p.post_id " +
                            "WHERE p.post_id = ? " +
                            "GROUP BY p.post_id, p.post_text, p.post_date, u.user_name"
            );

            statement.setLong(1, me.getId());
            statement.setLong(2, postId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                PostDto post = new PostDto();
                post.setId(resultSet.getLong(1));
                post.setText(resultSet.getString(2));
                post.setDate(new java.util.Date(resultSet.getTimestamp(3).getTime()));

                User user = new User();
                user.setUsername(resultSet.getString(4));

                post.setUser(user);

                post.setCommentsCount(resultSet.getLong(5));
                post.setLikesCount(resultSet.getLong(6));
                post.setLikedByMe(resultSet.getBoolean(7));

                return Optional.of(post);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve post from POSTS table", e);
        }
    }

    public void savePost(Post post) {
        if (savePost == null) {
            savePost = databaseService.prepareStatement(SAVE_POST_QUERY);
        }

        databaseService.withTransaction(c -> {
            try {
                savePost.setString(1, post.getText());
                savePost.setTimestamp(2, new Timestamp(post.getDate().getTime()));
                savePost.setLong(3, post.getUser().getId());

                savePost.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot update value in USERS table", e);
            }
        });
    }

    public Post findPostById(long id) {
        if (findPostById == null) {
            findPostById = databaseService.prepareStatement(FIND_POST_BY_ID);
        }

        try {
            findPostById.setLong(1, id);

            ResultSet resultSet = findPostById.executeQuery();

            if (resultSet.next()) {
                Post post = new Post();

                post.setId(resultSet.getLong("post_id"));
                post.setText(resultSet.getString("post_text"));
                post.setDate(new Date(resultSet.getTimestamp("post_date").getTime()));

                return post;
            } else {
                throw new RuntimeException("Cannot retrieve Post from POSTS table");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve Post from POSTS table", e);
        }
    }
}
