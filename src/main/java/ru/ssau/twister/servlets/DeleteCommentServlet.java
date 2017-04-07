package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.CommentDao;
import ru.ssau.twister.domain.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="DeleteCommentServlet", urlPatterns = "/deleteComment")
public class DeleteCommentServlet extends HttpServlet {
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long commentId = Long.parseLong(request.getParameter("commentId"));

            Comment comment = new Comment();

            comment.setId(commentId);

            commentDao.deleteComment(comment);

            response.sendRedirect("/posts/" + request.getParameter("postId"));
        } catch (Exception e) {
            response.sendError(500);
        }
    }
}
