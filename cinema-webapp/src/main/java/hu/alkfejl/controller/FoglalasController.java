package hu.alkfejl.controller;


import hu.alkfejl.dao.*;
import hu.alkfejl.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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


@WebServlet("/Foglalas_Controller")
public class FoglalasController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HallDAO hall = new HallDAOImpl();
    private TicketDAO tickets = new TicketDAOImpl();
    private ScreeningDAO screenings = new ScreeningDAOImpl();

    public FoglalasController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String vetites_id = request.getParameter("vetites_id");
        String jegyar = request.getParameter("jegyar");
        String helyek = request.getParameter("helyek");
        String idopont = request.getParameter("idopont");

        if(email == null || vetites_id==null || jegyar == null || helyek==null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }

        Ticket t = new Ticket();
        t.setEmail(email);
        t.setSzekek(helyek);
        t.setJegyar(Integer.parseInt(jegyar));
        t.setVetites_id(Integer.parseInt(vetites_id));
        t.setDatum(LocalDate.parse(idopont.split(" ")[0]));
        t.setNap(idopont.split(" ")[1]);
        tickets.add(t);
        response.sendRedirect("pages/foglalasaim.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");

        String screeningID = request.getParameter("screeningID");
        String hallName = request.getParameter("hallname");
        if(screeningID == null || hallName==null || user == null){
            request.getRequestDispatcher("pages/login.jsp").forward(request,response);
            return;
        }


        List<String> foglaltSzekek = new ArrayList<>();
        List<Ticket> ticketList = tickets.allTicket();

        Hall h = hall.specificHall(hallName);
        Screening specificID = screenings.specificScreening(Integer.parseInt(screeningID));

        ModositasController.FillReservedSeats(request, foglaltSzekek, ticketList, specificID, h);
        request.setAttribute("sor",h.getSor());
        request.setAttribute("oszlop",h.getOszlop());
        request.setAttribute("jegyar",specificID.getJegyar());
        request.setAttribute("email",user.getEmail());
        request.setAttribute("vetites_id",specificID.getId());
        request.setAttribute("idopont",specificID.getDatum().toString()+" "+specificID.getNap());



    }
}
