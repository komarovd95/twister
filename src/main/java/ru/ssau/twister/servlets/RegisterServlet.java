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
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegisterServlet", urlPatterns = "/registerRequest")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

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
            ) {
                @Override
                public String getErrorMessage() {
                    StringJoiner joiner = new StringJoiner("<br>");
                    String superError = super.getErrorMessage();

                    if (superError != null) {
                        joiner.add(superError);
                    }

                    if (Arrays.asList("login", "loginRequest", "register", "registerRequest", "edit", "search",
                            "styles", "vendor", "images").contains(getUsername())) {
                        joiner.add("Такой пользователь уже существует");
                    }

                    return joiner.length() == 0 ? null : joiner.toString();
                }
            };

            String errorMessage = credentials.getErrorMessage();

            if (errorMessage != null) {
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect("/register");
                return;
            }

            Optional<User> user = userDao.findByUsername(credentials.getUsername());

            if (user.isPresent()) {
                session.setAttribute("errorMessage", "Такой пользователь уже существует");
                response.sendRedirect("/register");
            } else {
                user = userDao.saveUser(credentials.getUsername(), credentials.getPassword());

                user.ifPresent(u -> session.setAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME, u));
                response.sendRedirect(redirectUrl);
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
