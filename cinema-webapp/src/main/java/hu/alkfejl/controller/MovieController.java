package hu.alkfejl.controller;


import hu.alkfejl.dao.MovieDAO;
import hu.alkfejl.dao.MovieDAOImpl;
import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOImpl;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Movie_Controller")
public class MovieController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Movie> m;
    MovieDAO movie = new MovieDAOImpl();

    public MovieController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        m = movie.allMovie();
        for(Movie a : m){
            String url = "data:image/png;base64,"+a.getBoritokep();
            a.setBoritokep(url);
        }
        request.setAttribute("movieList",m);
    }
}
