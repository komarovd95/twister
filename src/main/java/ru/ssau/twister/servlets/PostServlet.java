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
import java.util.NoSuchElementException;
import java.util.Optional;

@WebServlet(name = "PostServlet", urlPatterns = "/posts/*")
public class PostServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getPathInfo().substring(1));
            User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

            Optional<Post> post = postDao.findPostById(id, user);

            if (post.isPresent()) {
                request.setAttribute("post", post.get());
                request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);
            } else {
                throw new NoSuchElementException();
            }
        } catch (NumberFormatException | NoSuchElementException ignored) {
            response.sendError(404);
        }

    }
}
