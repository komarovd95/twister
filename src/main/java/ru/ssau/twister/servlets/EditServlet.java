package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.UserDao;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.dto.UserCredentials;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "EditServlet", urlPatterns = "/editRequest")
@MultipartConfig
public class EditServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EditServlet.class.getName());

    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            User user = (User) session.getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

            UserCredentials credentials = new UserCredentials(
                    user.getUsername(),
                    request.getParameter("password")
            );

            String errorMessage = credentials.getErrorMessage();

            Part avatarPart = request.getPart("avatar");

            String contentType = avatarPart.getContentType();
            long avatarSize = avatarPart.getSize();

            if (avatarSize > 0 && !contentType.startsWith("image/")) {
                session.setAttribute("errorMessage", "Неверный формат изображения");
                response.sendRedirect("/edit");
                return;
            }

            if (errorMessage != null && avatarSize == 0) {
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect("/edit");
                return;
            }

            if (avatarSize > 0) {
                byte[] avatarBytes = new byte[(int) avatarSize];
                avatarPart.getInputStream().read(avatarBytes);

                String encoded = String.format("data:%s;base64,%s", contentType,
                        Base64.getEncoder().encodeToString(avatarBytes));

                user.setAvatar(encoded.getBytes());
            }

            if (credentials.getPassword() != null && !credentials.getPassword().equals("")) {
                user.setPassword(credentials.getPassword());
            }

            userDao.updateUser(user);

            session.setAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME, user);

            response.sendRedirect("/");
        } catch (Exception e) {
            response.sendError(500);
            logger.log(Level.SEVERE, "Exception in LoginServlet", e);
        }
    }
}
