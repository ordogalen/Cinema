package hu.alkfejl.controller;

import com.mysql.cj.Session;
import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOImpl;
import hu.alkfejl.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/Login_Controller")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    UserDAO user = new UserDAOImpl();

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        User u = user.login(username,pass);
        if(u == null){
            response.sendRedirect("pages/login.jsp");
            return;
        }
        request.getSession().setAttribute("currentUser", u);
        response.sendRedirect("pages/filmek.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User u = (User) req.getSession().getAttribute("currentUser");
        req.setAttribute("personName", u.getNev());
    }
}
