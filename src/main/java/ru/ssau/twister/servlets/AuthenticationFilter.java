package ru.ssau.twister.servlets;

import ru.ssau.twister.utils.ApplicationConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    private static final String[] NON_PROTECTED_PATHS = new String[] {
            "^/$",
            "^/login$",
            "^/loginRequest",
            "^/register$",
            "^/registerRequest",
            "^/images/",
            "^/js/",
            "^/styles/",
            "^/vendor/",
            "^/favicon.ico$"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        String uri = httpRequest.getRequestURI();
        Object user = session.getAttribute(ApplicationConstants.USER_ATTRIBUTE_NAME);

        boolean matches = Arrays.stream(NON_PROTECTED_PATHS).map(Pattern::compile)
                .noneMatch(p -> p.matcher(uri).find());

        if (matches && user == null) {
            session.setAttribute("redirectUrl", uri);
            ((HttpServletResponse) response).sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}
