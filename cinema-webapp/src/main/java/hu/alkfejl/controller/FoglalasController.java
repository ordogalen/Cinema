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
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String vetites_id = request.getParameter("vetites_id");
        String jegyar = request.getParameter("jegyar");
        String helyek = request.getParameter("helyek");
        if(email == null || vetites_id==null || jegyar == null || helyek==null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        Ticket t = new Ticket();
        t.setEmail(email);
        t.setSzekek(helyek);
        t.setJegyar(Integer.parseInt(jegyar));
        t.setVetites_id(Integer.parseInt(vetites_id));
        tickets.add(t);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User u = (User) request.getSession().getAttribute("currentUser");

        String screeningID = request.getParameter("value");
        String hallName = request.getParameter("hallname");
        if(screeningID == null || hallName==null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        User user = (User) request.getSession().getAttribute("currentUser");
        if(user == null){
            request.getRequestDispatcher("pages/filmek.jsp").forward(request,response);
            return;
        }
        List<String> foglaltSzekek = new ArrayList<>();
        List<Ticket> ticketList = tickets.allTicket();

        Hall h = hall.specificHall(hallName);
        Screening specificID = screenings.specificScreening(Integer.parseInt(screeningID));

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
        request.setAttribute("sor",h.getSor());
        request.setAttribute("oszlop",h.getOszlop());
        request.setAttribute("jegyar",specificID.getJegyar());
        request.setAttribute("email",u.getEmail());
        request.setAttribute("vetites_id",specificID.getId());



    }
}
