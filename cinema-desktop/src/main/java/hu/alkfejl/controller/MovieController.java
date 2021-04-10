package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.MovieDAO;
import hu.alkfejl.dao.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieController implements Initializable {
    @FXML
    Label tempFilmCim, tempFilmLeiras, tempFilmHossz;
    @FXML
    TextField filmCim, filmRendezo, filmHossz, filmKorhatar, filmSzereplok;

    @FXML
    TextArea filmLeiras;
    @FXML
    Button filmInsert, filmDelete, filmUpdate;
    @FXML
    Button imgLoad;
    @FXML
    ImageView imgV;
    @FXML
    AnchorPane rightPaneBack;
    @FXML
    Label filmCimLabel, filmHosszLabel, filmLeirasLabel, filmKorhatarLabel, filmBoritokepLabel;



    String encodedfile;
    int codeMessage;

    MovieDAO Movies = new MovieDAOImpl();

    private void setToDefault(){
        filmCim.setText("");
        filmRendezo.setText("");
        filmHossz.setText("");
        filmKorhatar.setText("");
        filmSzereplok.setText("");
        filmLeiras.setText("");
        imgV.setImage(null);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filmCim.setPromptText("Add meg a film címét!");
        filmLeiras.setPromptText("Add meg a film leírását!");
        filmHossz.setPromptText("Add meg a film hosszát!");
        tempFilmCim.textProperty().bind(filmCim.textProperty());
        tempFilmLeiras.textProperty().bind(filmLeiras.textProperty());
        tempFilmHossz.textProperty().bind(filmHossz.textProperty().concat(" perc"));
        tempFilmLeiras.setAlignment(Pos.TOP_LEFT);
        ButtonHandler();
    }


    private void ButtonHandler(){
        //    Label filmCimLabel, filmHosszLabel, filmLeirasLabel, filmKorhatarLabel, filmBoritokepLabel;
        filmInsert.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            filmCimLabel.setText(filmCimLabel.getText()+"*");
            filmHosszLabel.setText(filmHosszLabel.getText()+"*");
            filmLeirasLabel.setText(filmLeirasLabel.getText()+"*");
            filmKorhatarLabel.setText(filmKorhatarLabel.getText()+"*");
            filmBoritokepLabel.setText(filmBoritokepLabel.getText()+"*");
        });
        filmInsert.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            filmCimLabel.setText(filmCimLabel.getText().substring(0,filmCimLabel.getText().length()-1));
            filmHosszLabel.setText(filmHosszLabel.getText().substring(0,filmHosszLabel.getText().length()-1));
            filmLeirasLabel.setText(filmLeirasLabel.getText().substring(0,filmLeirasLabel.getText().length()-1));
            filmKorhatarLabel.setText(filmKorhatarLabel.getText().substring(0,filmKorhatarLabel.getText().length()-1));
            filmBoritokepLabel.setText(filmBoritokepLabel.getText().substring(0,filmBoritokepLabel.getText().length()-1));
        });
        filmUpdate.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            filmCimLabel.setText(filmCimLabel.getText()+"*");
        });
        filmUpdate.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            filmCimLabel.setText(filmCimLabel.getText().substring(0,filmCimLabel.getText().length()-1));
        });
    }

    private Movie makeMovie(){
        Movie m = new Movie();
        m.setFilm_nev(filmCim.getText());
        m.setHossz(filmHossz.getText());
        m.setKorhatar(filmKorhatar.getText());
        m.setLeiras(filmLeiras.getText());
        m.setRendezo(filmRendezo.getText());
        m.setBoritokep(encodedfile);
        m.setSzereplok(filmSzereplok.getText());
        return m;
    }

    public void imageToByte(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Válassz egy boritokepet");
        File file = fileChooser.showOpenDialog(App.stage);
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            imgV.setImage(image1);
            FileInputStream fileInputStreamReader = null;
            try {
                fileInputStreamReader = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[(int)file.length()];
            try {
                fileInputStreamReader.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            encodedfile = Base64.getEncoder().encodeToString(bytes);
        }
    }

    private void decodeMessage(int value){
        Alert msg;
        if(value == 0){
            msg = new Alert(Alert.AlertType.INFORMATION, "Sikeres művelet");
        }
        else if(value == 1){
            msg = new Alert(Alert.AlertType.INFORMATION,"Sikertelen művelet");
        }else{
            msg = new Alert(Alert.AlertType.INFORMATION,"Győződj meg róla, hogy minden adatot helyesn adtál-e meg");
        }
        msg.showAndWait();
    }

    public void update(ActionEvent actionEvent) {
        codeMessage = Movies.update(makeMovie());
        setToDefault();
        decodeMessage(codeMessage);
    }

    public void feltolt(ActionEvent actionEvent) {
        codeMessage = Movies.insert(makeMovie());
        setToDefault();
        decodeMessage(codeMessage);
    }





    //NAVIGATION
    @FXML
    public void toFilm(ActionEvent actionEvent)  {
        App.loadFXML("/fxml/eddigi");
    }
    public void toVetites(ActionEvent actionEvent) {
        App.loadFXML("/fxml/vetites");
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
