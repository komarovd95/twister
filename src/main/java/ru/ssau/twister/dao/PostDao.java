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
    private final DatabaseService databaseService;

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
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "INSERT INTO posts (post_text, post_date, post_user_id) VALUES (?, ?, ?)"
            );

            statement.setString(1, post.getText());
            statement.setTimestamp(2, new Timestamp(post.getDate().getTime()));
            statement.setLong(3, post.getUser().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save post in POSTS table", e);
        }
    }

    public void likePost(Post post, User liker) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "INSERT INTO posts_likes (post_id, user_id) VALUES(?, ?)"
            );

            statement.setLong(1, post.getId());
            statement.setLong(2, liker.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot like post", e);
        }
    }

    public void unlikePost(Post post, User liker) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "DELETE FROM posts_likes WHERE post_id = ? AND user_id = ?"
            );

            statement.setLong(1, post.getId());
            statement.setLong(2, liker.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot unlike post", e);
        }
    }
}
