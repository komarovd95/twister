package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.UserDao;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EditFormServlet", urlPatterns = "/edit")
public class EditFormServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init(ServletConfig filterConfig) throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

        Optional<byte[]> userAvatar = userDao.findUserById(user.getId()).map(User::getAvatar);

        String avatar = userAvatar.map(String::new)
                .orElseGet(() -> request.getContextPath() + "/images/default-user-image.png");

        request.setAttribute("avatar", avatar);
        request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
    }
}