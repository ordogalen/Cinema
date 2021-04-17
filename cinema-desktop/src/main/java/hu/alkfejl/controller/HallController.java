package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.HallDAO;
import hu.alkfejl.dao.HallDAOImpl;
import hu.alkfejl.model.Hall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HallController implements Initializable {
    @FXML
    TableView<Hall> teremTable;
    @FXML
    TableColumn<Hall, String> Terem_nev;
    @FXML
    TableColumn<Hall, Integer> Terem_sor, Terem_oszlop;
    @FXML
    TextField teremNev, teremSor,teremOszlop;
    @FXML
    Label teremNevLabel, teremSorLabel, teremOszlopLabel;
    @FXML
    Button teremInsert,teremUpdate,teremDelete;

    HallDAO Halls = new HallDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //MAke the table clickable and get the terem_nev
        teremTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2)
            {
                teremNev.setText(teremTable.getSelectionModel().getSelectedItem().getTerem_nev());
            }
        });
        ButtonHandler();
        showAll();

    }

    private void ButtonHandler(){
        teremInsert.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText()+"*");
            teremOszlopLabel.setText(teremOszlopLabel.getText()+"*");
            teremSorLabel.setText(teremSorLabel.getText()+"*");
        });
        teremInsert.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText().substring(0,teremNevLabel.getText().length()-1));
            teremOszlopLabel.setText(teremOszlopLabel.getText().substring(0,teremOszlopLabel.getText().length()-1));
            teremSorLabel.setText(teremSorLabel.getText().substring(0,teremSorLabel.getText().length()-1));
        });

        teremUpdate.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText()+"*");
        });
        teremUpdate.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText().substring(0,teremNevLabel.getText().length()-1));
        });

        teremDelete.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText()+"*");
        });
        teremDelete.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            teremNevLabel.setText(teremNevLabel.getText().substring(0,teremNevLabel.getText().length()-1));
        });
    }

    private void showAll(){
        teremTable.getItems().setAll(Halls.allHall());
        Terem_nev.setCellValueFactory(new PropertyValueFactory<>("terem_nev"));
        Terem_sor.setCellValueFactory(new PropertyValueFactory<>("sor"));
        Terem_oszlop.setCellValueFactory(new PropertyValueFactory<>("oszlop"));
        teremNev.setText("");
        teremOszlop.setText("");
        teremSor.setText("");
    }

    private boolean Checker(){
        if(teremNev.getText() == null || teremSor.getText()==null || teremOszlop.getText()==null){
            Alert conf = new Alert(Alert.AlertType.INFORMATION, "Nem töltöttél ki minden mezőt");
            conf.showAndWait();
            return false;
        }
        return true;
    }

    private Hall new_Hall(){
        Hall h = new Hall();
        h.setTerem_nev(teremNev.getText());
        h.setSor(Integer.parseInt(teremSor.getText()));
        h.setOszlop(Integer.parseInt(teremOszlop.getText()));
        return h;
    }

    @FXML
    private void deleteHall(){
        Hall h = new Hall();
        h.setTerem_nev(teremNev.getText());
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törölni akarod ezt a termet: "+h.getTerem_nev(), ButtonType.YES, ButtonType.NO);
        conf.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                Halls.delete(h);
            }
        });
        showAll();
    }

    @FXML
    private void insertHall(){
        if(!Checker()){
            return;
        }
        Halls.insert(new_Hall());
        showAll();
    }

    @FXML
    private void updateHall(){
        Hall h = new Hall();
        h.setTerem_nev(teremNev.getText());
        Halls.udpate(h,teremTable.getSelectionModel().getSelectedItem().getTerem_nev());
        showAll();
    }



    // BUTTON sTUFF
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
