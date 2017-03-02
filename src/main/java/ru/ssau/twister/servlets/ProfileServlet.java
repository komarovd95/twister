package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.PostDao;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getAttribute("userProfile");

        if (user != null) {
            String avatar;

            if (user.getAvatar() == null) {
                avatar = request.getContextPath() + "/images/default-user-image.png";
            } else {
                avatar = "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
            }

            request.setAttribute("avatar", avatar);

            List<Post> posts = postDao.findAllPostsByUser(user);
            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        } else {
            response.sendError(400);
        }
    }
}
