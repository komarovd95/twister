package ru.ssau.twister.dao;

import ru.ssau.twister.domain.Comment;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.CommentDto;
import ru.ssau.twister.services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private final DatabaseService databaseService;

    public CommentDao() {
        databaseService = DatabaseService.getInstance();
    }

    public List<Comment> findAllCommentsByPost(Post post, User me) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "SELECT c.comment_id, c.comment_text, c.comment_date, u.user_name, " +
                            "count(cl.*), bool_or(cl.user_id = ?) " +
                            "FROM comments c " +
                            "INNER JOIN users u ON c.comment_user_id = u.user_id " +
                            "LEFT OUTER JOIN comments_likes cl ON cl.comment_id = c.comment_id " +
                            "WHERE c.comment_post_id = ? " +
                            "GROUP BY c.comment_id, c.comment_text, c.comment_date, u.user_name"
            );

            statement.setLong(1, me.getId());
            statement.setLong(2, post.getId());

            ResultSet resultSet = statement.executeQuery();
            List<Comment> comments = new ArrayList<>();

            while (resultSet.next()) {
                CommentDto comment = new CommentDto();
                comment.setId(resultSet.getLong(1));
                comment.setText(resultSet.getString(2));
                comment.setDate(new java.util.Date(resultSet.getTimestamp(3).getTime()));
                comment.setPost(post);

                User user = new User();
                user.setUsername(resultSet.getString(4));

                comment.setUser(user);

                comment.setLikesCount(resultSet.getLong(5));
                comment.setLikedByMe(resultSet.getBoolean(6));

                comments.add(comment);
            }

            return comments;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot retrieve comments from COMMENTS table", e);
        }
    }

    public void saveComment(Comment comment) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "INSERT INTO comments (comment_text, comment_date, comment_user_id, comment_post_id) " +
                            "VALUES (?, ?, ?, ?)"
            );

            statement.setString(1, comment.getText());
            statement.setTimestamp(2, new Timestamp(comment.getDate().getTime()));
            statement.setLong(3, comment.getUser().getId());
            statement.setLong(4, comment.getPost().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save comment in COMMENTS table", e);
        }
    }

    public void deleteComment(Comment comment) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "DELETE FROM comments WHERE comment_id = ?"
            );

            statement.setLong(1, comment.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete comment from COMMENTS table", e);
        }
    }

    public void likeComment(Comment comment, User liker) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "INSERT INTO comments_likes (comment_id, user_id) VALUES(?, ?)"
            );

            statement.setLong(1, comment.getId());
            statement.setLong(2, liker.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot like comment", e);
        }
    }

    public void unlikeComment(Comment comment, User liker) {
        try {
            PreparedStatement statement = databaseService.prepareStatement(
                    "DELETE FROM comments_likes WHERE comment_id = ? AND user_id = ?"
            );

            statement.setLong(1, comment.getId());
            statement.setLong(2, liker.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot unlike comment", e);
        }
    }
}

