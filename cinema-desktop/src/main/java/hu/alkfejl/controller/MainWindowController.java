package hu.alkfejl.controller;

import hu.alkfejl.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I AM IN");
    }



    // NAVIGATION
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
