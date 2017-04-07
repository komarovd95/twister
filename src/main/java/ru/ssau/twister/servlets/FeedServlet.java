package ru.ssau.twister.servlets;

import ru.ssau.twister.dao.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FeedServlet", urlPatterns = "/feed")
public class FeedServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
