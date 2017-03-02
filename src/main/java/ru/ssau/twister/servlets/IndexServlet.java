package ru.ssau.twister.servlets;

import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = "")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/landing.jsp").forward(request, response);
        } else {
            request.setAttribute("userProfile", user);
            request.getRequestDispatcher("/profile").forward(request, response);
        }
    }
}
