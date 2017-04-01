package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.PostDao;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "PostsServlet", urlPatterns = "/posts")
public class PostsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PostsServlet.class.getName());

    private final PostDao postDao = new PostDao();

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

            String postText = request.getParameter("text");

            if (postText != null && postText.length() != 0) {
                Post post = new Post();
                post.setText(postText);
                post.setUser(user);
                post.setDate(new Date());

                postDao.savePost(post);
            }

            response.sendRedirect("/");
        } catch (Exception e) {
            response.sendError(500);
            logger.log(Level.SEVERE, "Exception in PostsServlet", e);
        }
    }
}
