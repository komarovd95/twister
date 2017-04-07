package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.CommentDao;
import ru.ssau.twister.dao.PostDao;
import ru.ssau.twister.domain.Comment;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UnlikeServlet", urlPatterns = "/unlike")
public class UnlikeServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

            String postIdParameter = request.getParameter("postId");
            String commentIdParameter = request.getParameter("commentId");

            if (postIdParameter != null) {
                long postId = Long.parseLong(postIdParameter);

                Post post = new Post();
                post.setId(postId);

                postDao.unlikePost(post, user);
            } else if (commentIdParameter != null) {
                long commentId = Long.parseLong(commentIdParameter);

                Comment comment = new Comment();
                comment.setId(commentId);

                commentDao.unlikeComment(comment, user);
            } else {
                throw new IllegalArgumentException();
            }

            response.sendRedirect(request.getParameter("redirectTo"));
        } catch (Exception e) {
            response.sendError(500);
        }
    }
}
