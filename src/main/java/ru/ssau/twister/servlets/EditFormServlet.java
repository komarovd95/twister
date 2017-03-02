package ru.ssau.twister.servlets;

import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "EditFormServlet", urlPatterns = "/edit")
public class EditFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

        String avatar;
        if (user.getAvatar() == null) {
            avatar = request.getContextPath() + "/images/default-user-image.png";
        } else {
            avatar = "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getAvatar());
        }

        request.setAttribute("avatar", avatar);
        request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
    }
}