package hu.alkfejl.controller;


import hu.alkfejl.dao.*;
import hu.alkfejl.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Foglalasaim_Controller")
public class FoglalasaimController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    TicketDAO ticket = new TicketDAOImpl();


    public FoglalasaimController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");
        if(user == null){
            request.getRequestDispatcher("pages/login.jsp").forward(request,response);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");
        if(user == null){
            request.getRequestDispatcher("pages/login.jsp").forward(request,response);
            return;
        }




        String torlesID = request.getParameter("value");
        if(torlesID != null){
            Ticket torlendo = new Ticket();
            torlendo.setJegy_id(Integer.parseInt(torlesID));
            ticket.delete(torlendo);
            response.sendRedirect("pages/filmek.jsp");
        }

        List<Ticket> tempTickets = ticket.allTicket();;
        List<Ticket> tickets = new ArrayList<>();

        String mit = request.getParameter("mit");
        String miben = request.getParameter("miben");

        if(mit != null || miben != null){
            tempTickets = ticket.Search(miben,mit,user.getEmail());
            for(Ticket i : tempTickets){
                if(i.getEmail().equals(user.getEmail())){
                    tickets.add(i);
                }
            }
            request.setAttribute("sclist",tickets);
            return;
        }


        for(Ticket i : tempTickets){
            if(i.getEmail().equals(user.getEmail())){
                tickets.add(i);
            }
        }
        request.setAttribute("sclist",tickets);
    }
}
