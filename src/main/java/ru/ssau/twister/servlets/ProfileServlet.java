package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.PostDao;
import ru.ssau.twister.dao.UserDao;
import ru.ssau.twister.domain.Post;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.UserDto;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);
        UserDto user = (UserDto) request.getAttribute("userProfile");

        if (user != null) {
            String avatar;

            if (user.getAvatar() == null) {
                avatar = request.getContextPath() + "/images/default-user-image.png";
            } else {
                avatar = new String(user.getAvatar());
            }

            request.setAttribute("avatar", avatar);
            request.setAttribute("itsMe", loggedUser != null && loggedUser.getId().equals(user.getId()));

            List<Post> posts = postDao.findAllPostsByUser(user, loggedUser);
            request.setAttribute("posts", posts);

            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        } else {
            response.sendError(400);
        }
    }
}
