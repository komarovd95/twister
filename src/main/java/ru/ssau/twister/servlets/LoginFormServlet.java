package ru.ssau.twister.servlets;

import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginFormServlet", urlPatterns = "/login")
public class LoginFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME) != null) {
            response.sendRedirect("/");
            return;
        }

        String redirectUrl = (String) session.getAttribute("redirectUrl");

        request.setAttribute("redirectUrl", ("".equals(redirectUrl) || redirectUrl == null) ? "/" : redirectUrl);
        request.setAttribute("errorMessage", session.getAttribute("errorMessage"));

        session.removeAttribute("errorMessage");

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
