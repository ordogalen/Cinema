package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.TicketDAO;
import hu.alkfejl.dao.TicketDAOImpl;
import hu.alkfejl.model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketController implements Initializable {
    @FXML
    Button updateJegy;
    @FXML
    TextField jegyAr, jegyMikor, jegySzekek;

    @FXML
    TableView<Ticket> jegyTable;
    @FXML
    TableColumn<Ticket, String> emailColumn, szekekColumn;
    @FXML
    TableColumn<Ticket, Integer> idColumn, arColumn, vetitesColumn;
    @FXML
    TableColumn<Ticket, String> mikorColumn;
    TicketDAO tickets = new TicketDAOImpl();

    Integer ticketID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAll();
        jegyTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2)
            {
                ticketID = jegyTable.getSelectionModel().getSelectedItem().getJegy_id();
                jegyAr.setText(String.valueOf(jegyTable.getSelectionModel().getSelectedItem().getJegyar()));
                jegySzekek.setText(jegyTable.getSelectionModel().getSelectedItem().getSzekek());
                jegyMikor.setText(jegyTable.getSelectionModel().getSelectedItem().getDatum() + " " + jegyTable.getSelectionModel().getSelectedItem().getNap());
                jegyMikor.setEditable(false);
            }
        });
    }

    private void showAll() {
        jegyTable.getItems().setAll(tickets.allTicket());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("jegy_id"));
        arColumn.setCellValueFactory(new PropertyValueFactory<>("jegyar"));
        vetitesColumn.setCellValueFactory(new PropertyValueFactory<>("vetites_id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        szekekColumn.setCellValueFactory(new PropertyValueFactory<>("szekek"));
        mikorColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
    }

    public void delete(ActionEvent actionEvent) {
        Alert conf;
        if(ticketID==null){
            conf = new Alert(Alert.AlertType.INFORMATION, "Kattints kétszer valamelyik sorra");
            conf.showAndWait();
            return;
        }
        Ticket t = new Ticket();
        t.setJegy_id(ticketID);
        conf = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törölni akarod ezt a jegyet: "+t.getJegy_id(), ButtonType.YES, ButtonType.NO);
        conf.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                tickets.delete(t);
            }
        });
        ticketID=null;
        showAll();
    }

    public void Update(ActionEvent actionEvent) {
        if(Objects.equals(jegySzekek.getText(), "") || Objects.equals(jegyAr.getText(), "")){
            Alert conf = new Alert(Alert.AlertType.INFORMATION,"Minden mező kitöltése szükséges!");
            conf.showAndWait();
            return;
        }
        Ticket t = new Ticket();
        t.setSzekek(jegySzekek.getText());
        t.setJegyar(Integer.parseInt(jegyAr.getText()));
        t.setJegy_id(ticketID);
        tickets.update(t);

        showAll();
        jegySzekek.setText("");
        jegyAr.setText("");
        jegyMikor.setText("");
    }

    //navigation
    public void toFilm(ActionEvent actionEvent)  {
        App.loadFXML("/fxml/eddigi");
    }
    public void toHome(ActionEvent actionEvent) {
        App.loadFXML("/fxml/primary");
    }
    public void toFoglalas(ActionEvent actionEvent) {
        App.loadFXML("/fxml/jegy");
    }
    public void toTerem(ActionEvent actionEvent) {
        App.loadFXML("/fxml/terem");
    }

    @FXML
    public void exit(MouseEvent actionEvent){ App.close();}

}
