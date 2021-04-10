package hu.alkfejl.filter;

import hu.alkfejl.model.User;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    public AuthFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User u = null;

        if (session != null) {
            u = (User) session.getAttribute("Login");
        }

        boolean isLoggedIn = (u != null);

        // Check if the user is accessing login page
        if (req.getRequestURI().equals(
                req.getContextPath() + "/pages/login.jsp")) {
            if (isLoggedIn) {
                // Redirect to landing or home page
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath()
                        + "/pages/filmek.jsp");
            } else {
                // Otherwise, nothing to do if he has not logged in
                // pass the request along the filter chain
                chain.doFilter(request, response);
            }
        } else {
            // For all other pages,
            if (isLoggedIn) {
                // Nothing to do
                chain.doFilter(request, response);
            } else {
                // Redirect to login page if he has not logged in
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
            }
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }

}