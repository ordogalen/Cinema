package hu.alkfejl.controller;

import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOImpl;
import hu.alkfejl.model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/Register_Controller")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDAO user = new UserDAOImpl();

    public RegisterController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String email = request.getParameter("email");
            String nev =request.getParameter("username");
            String jelszo = request.getParameter("password");
            if(email != null &&nev != null && jelszo !=null) {
                if(regex(email)){
                    User u = new User();
                    u.setEmail(email);
                    u.setNev(nev);
                    u.setJelszo(jelszo);
                    user.addUser(u);
                response.sendRedirect("pages/login.jsp");
                return;
                }
            }
        response.sendRedirect("pages/login.jsp?error=0");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean regex(String r){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(r);
        return matcher.find();
    }
}
