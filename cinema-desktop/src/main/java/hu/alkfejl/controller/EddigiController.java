package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.HallDAO;
import hu.alkfejl.dao.HallDAOImpl;
import hu.alkfejl.dao.MovieDAO;
import hu.alkfejl.dao.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EddigiController implements Initializable {
    @FXML
    TextField searchWhat;
    @FXML
    ChoiceBox<String> searchWherein;
    @FXML
    TableView<Movie> filmView;
    @FXML
    TableColumn<Movie,String> filmNev, filmHossz, filmKorhatar, filmLeiras, filmSzereplok, filmRendezo;
    MovieDAO Movies = new MovieDAOImpl();

    //After double click on filmView
    String movieName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filmView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2)
            {
                movieName = filmView.getSelectionModel().getSelectedItem().getFilm_nev();
            }
        });
        searchWherein.getItems().add("film_nev");
        searchWherein.getItems().add("korhatar");
        searchWherein.getItems().add("szereplok");
        searchWherein.getItems().add("rendezo");
        searchWherein.getItems().add("leiras");
        searchWherein.getItems().add("hossz");

        showAll();

    }

    private void showAll() {
        filmView.getItems().setAll(Movies.allMovie());
        filmNev.setCellValueFactory(new PropertyValueFactory<>("film_nev"));
        filmHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        filmKorhatar.setCellValueFactory(new PropertyValueFactory<>("korhatar"));
        filmLeiras.setCellValueFactory(new PropertyValueFactory<>("leiras"));
        filmSzereplok.setCellValueFactory(new PropertyValueFactory<>("szereplok"));
        filmRendezo.setCellValueFactory(new PropertyValueFactory<>("rendezo"));
    }

    public void keres(ActionEvent actionEvent){
        String whatin = searchWherein.getValue();
        List<Movie> lista = Movies.search(whatin, searchWhat.getText());
        if(!lista.isEmpty()){
            filmView.getItems().setAll(Movies.search(whatin, searchWhat.getText()));
            filmNev.setCellValueFactory(new PropertyValueFactory<>("film_nev"));
            filmHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
            filmKorhatar.setCellValueFactory(new PropertyValueFactory<>("korhatar"));
            filmLeiras.setCellValueFactory(new PropertyValueFactory<>("leiras"));
            filmSzereplok.setCellValueFactory(new PropertyValueFactory<>("szereplok"));
            filmRendezo.setCellValueFactory(new PropertyValueFactory<>("rendezo"));
            return;
        }
        Alert msg = new Alert(Alert.AlertType.INFORMATION,"Nem létezik ilyen adat az adatbázisban");
        msg.showAndWait();
        showAll();

    }

    public void delete(ActionEvent actionEvent) {
        Movie d = new Movie();
        d.setFilm_nev(movieName);
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törölni akarod ezt a filmet: "+d.getFilm_nev(), ButtonType.YES, ButtonType.NO);
        conf.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                Movies.delete(d);
            }
        });
        movieName="";
        showAll();
    }



    //navigation
    public void toModosit(ActionEvent actionEvent) {
        App.loadFXML("/fxml/film");
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
    public void toFilm(ActionEvent actionEvent) {
        App.loadFXML("/fxml/eddigi");
    }
    @FXML
    public void exit(MouseEvent actionEvent){ App.close();}



}
