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


@WebServlet("/Modositas_Controller")
public class ModositasController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HallDAO hall = new HallDAOImpl();
    private TicketDAO tickets = new TicketDAOImpl();
    private ScreeningDAO screenings = new ScreeningDAOImpl();


    public ModositasController() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String vetites_id = request.getParameter("vetites_id");
        String jegyar = request.getParameter("jegyar");
        String helyek = request.getParameter("helyek");
        String jegy_id = request.getParameter("jegy_id");

        if(email == null || vetites_id==null || jegyar == null || helyek==null || jegy_id == null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        Ticket t = new Ticket();
        t.setJegy_id(Integer.parseInt(jegy_id));
        t.setEmail(email);
        t.setSzekek(helyek);
        t.setJegyar(Integer.parseInt(jegyar));
        t.setVetites_id(Integer.parseInt(vetites_id));
        tickets.update(t);
        response.sendRedirect("pages/foglalasaim.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("currentUser");

        String screeningID = request.getParameter("screeningID");
        String helyek = request.getParameter("helyek");
        String jegyar = request.getParameter("jegyar");


        if(user == null){
            request.getRequestDispatcher("pages/login.jsp").forward(request,response);
            return;
        }
        if(screeningID == null || helyek==null || jegyar == null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        int ticketID = tickets.specificTicketID(Integer.parseInt(screeningID), helyek);
        if(ticketID==0){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }

        List<String> foglaltSzekek = new ArrayList<>();
        List<Ticket> ticketList = tickets.allTicket();

        Screening specificID = screenings.specificScreening(Integer.parseInt(screeningID));
        Hall h = hall.specificHall(specificID.getTerem_nev());

        FillReservedSeats(request, foglaltSzekek, ticketList, specificID, h);

        request.setAttribute("aktualisSzekek",helyek);
        request.setAttribute("sor",h.getSor());
        request.setAttribute("oszlop",h.getOszlop());
        request.setAttribute("jegyar",specificID.getJegyar()/helyek.split(",").length);
        request.setAttribute("email",user.getEmail());
        request.setAttribute("vetites_id",specificID.getId());
        request.setAttribute("jegy_id",ticketID);
        request.setAttribute("idopont",specificID.getDatum().toString()+" "+specificID.getNap());




    }

    static void FillReservedSeats(HttpServletRequest request, List<String> foglaltSzekek, List<Ticket> ticketList, Screening specificID, Hall h) {
        for (Ticket t : ticketList){
            if(specificID.getId()==t.getVetites_id()){
                foglaltSzekek.addAll(Arrays.asList(t.getSzekek().split(",")));
            }
        }

        int[] osszesSzek = new int[h.getOszlop()*h.getSor()];
        for(int i = 0; i<(h.getSor()*h.getOszlop());i++){
            if(foglaltSzekek.contains(String.valueOf(i))) {
                osszesSzek[i] = 1;
                continue;
            }
            osszesSzek[i]=0;
        }

        request.setAttribute("szekek",osszesSzek);
    }
}
