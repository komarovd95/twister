package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.CommentDao;
import ru.ssau.twister.domain.Comment;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name="CommentServlet", urlPatterns = "/comment")
public class CommentServlet extends HttpServlet {
    private CommentDao commentDao;

    @Override
    public void init(ServletConfig filterConfig) throws ServletException {
        commentDao = new CommentDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

            String commentText = request.getParameter("text");
            Long postId = Long.parseLong(request.getParameter("post-id"));

            if (commentText != null && commentText.length() != 0) {
                Comment comment = new Comment();

                comment.setText(commentText);
                comment.setUser(user);
                comment.setDate(new Date());

                Post post = new Post();
                post.setId(postId);

                comment.setPost(post);

                commentDao.saveComment(comment);
            }

            response.sendRedirect("/posts/" + postId);
        } catch (Exception e) {
            response.sendError(500);
        }
    }
}
