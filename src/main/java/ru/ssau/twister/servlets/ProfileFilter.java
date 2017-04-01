package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.UserDao;
import ru.ssau.twister.domain.User;
import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

@WebFilter(filterName = "ProfileFilter", urlPatterns = "/*")
public class ProfileFilter implements Filter {
    private static final Pattern PATTERN = Pattern.compile("^/[a-zA-Z0-9_]{4,20}$");
    private static final String[] PRESERVED_PATHS = new String[] {
            "/login", "/loginRequest", "/register", "/edit", "/search"
    };

    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = new UserDao();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();

        if (PATTERN.matcher(uri).matches() && Arrays.stream(PRESERVED_PATHS).noneMatch(s -> s.equals(uri))) {
            Optional<User> user = userDao.findUserFullGraphByName(
                    uri.substring(1),
                    ((User) ((HttpServletRequest) request).getSession()
                            .getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME)));

            if (user.isPresent()) {
                request.setAttribute("userProfile", user.get());
                request.getRequestDispatcher("/profile").forward(request, response);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
