package hu.alkfejl.controller;


import hu.alkfejl.dao.*;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;
import hu.alkfejl.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Screening_Controller")
public class ScreeningController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MovieDAO movies = new MovieDAOImpl();
    private ScreeningDAO screenings = new ScreeningDAOImpl();
    private List<Screening> screening;

    public ScreeningController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieName = request.getParameter("value");
        if(movieName == null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        Movie movie = movies.specificMovie(movieName);
        screening = screenings.movieScreening(movieName);
        request.setAttribute("screeningList",screening);
        request.setAttribute("Movie",movie);

    }
}
