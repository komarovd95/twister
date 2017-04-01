package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.UserDao;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.UserCredentials;
import ru.ssau.twister.utils.ApplicationConstants;
import ru.ssau.twister.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginRequest")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            String redirectUrl = getRedirectUrl(request.getQueryString());

            UserCredentials credentials = new UserCredentials(
                    request.getParameter("username"),
                    request.getParameter("password")
            );

            String errorMessage = credentials.getErrorMessage();

            if (errorMessage != null) {
                session.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
                response.sendRedirect("/login");
                return;
            }

            Optional<User> user = userDao.findUserCredentialsByName(credentials.getUsername());

            if (user.map(User::getPassword).filter(p -> p.equals(credentials.getPassword())).isPresent()) {
                session.setAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME, user.get());
                response.sendRedirect(redirectUrl);
            } else {
                session.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Неверная пара логин/пароль");
                response.sendRedirect("/login");
            }
        } catch (Exception e) {
            response.sendError(500);
            logger.log(Level.SEVERE, "Exception in LoginServlet", e);
        }
    }

    private static String getRedirectUrl(String queryString) {
        if (queryString == null) {
            return "/";
        }

        Object redirectParameter = WebUtils.parseQueryString(queryString).get("redirect");

        if (redirectParameter instanceof String) {
            String redirectUrl = (String) redirectParameter;

            if (redirectUrl.charAt(0) == '/') {
                return redirectUrl;
            } else {
                return "/" + redirectParameter;
            }
        } else {
            return "/";
        }
    }
}
