package hu.alkfejl.controller;

import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOImpl;
import hu.alkfejl.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Register_Controller")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDAO user = new UserDAOImpl();

    public RegisterController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            User u = new User();
            u.setEmail(request.getParameter("email"));
            u.setNev(request.getParameter("username"));
            u.setJelszo(request.getParameter("password"));
            user.addUser(u);
            response.sendRedirect("pages/login.jsp");

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
