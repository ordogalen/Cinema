package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.ScreeningDAO;
import hu.alkfejl.dao.ScreeningDAOImpl;
import hu.alkfejl.model.Screening;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ScreeningController implements Initializable{
    @FXML
    Button vetitesBeszur;
    @FXML
    ChoiceBox<String> whatTimeBox, whatTeremBox, whatScreeningID;
    @FXML
    GridPane imgViewer;
    @FXML
    DatePicker whatDayPicker;
    @FXML
    Label filmLabelNev;
    @FXML
    TextField whatJegyar;

    Map<Image, String> ImageNameMap = new HashMap<Image, String>();

    ScreeningDAO Screenings = new ScreeningDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FillUpTheMap();
        imageSelector();
        fillChoiceBox();
    }


    //ADD IMAGES TO GRID AND IF CLICKED GET THE MOVIE NAME
    private void FillUpTheMap() {
        Map<InputStream, String> temp = Screenings.ImageNameMap();
        for (Map.Entry<InputStream, String> entry : temp.entrySet()) {
            InputStream in = Base64.getDecoder().wrap(entry.getKey());
            Image im = new Image(in);
            ImageNameMap.put(im, entry.getValue());
        }
    }

    @FXML
    private void imageSelector() {
        for (Map.Entry<Image, String> entry : ImageNameMap.entrySet()) {
            boolean inserted = false;
            for (int i = 0; i < imgViewer.getRowCount(); i++) {
                if (inserted) {
                    break;
                }
                for (int j = 0; j < imgViewer.getColumnCount(); j++) {
                    if (!getNode(j, i)) {
                        imgViewer.add(doImage(entry.getKey()), j, i);
                        inserted = true;
                        break;
                    }
                }
            }
        }
    }

    //MAKING THE IMAGEVIEW, SIZING THEN ADDING THE EVENTLISTENER
    private ImageView doImage(Image image){
        ImageView ig = new ImageView(image);
        ig.setFitWidth(170);
        ig.setFitHeight(132);
        ig.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            filmLabelNev.setText(ImageNameMap.get(image));
            event.consume();
        });

        return ig;
    }

    //CHECKING IF THE ACTUAL GRID HAS A NODE
    private boolean getNode(int row, int column){
        for (Node node : imgViewer.getChildren()) {
            if (GridPane.getColumnIndex(node) == row && GridPane.getRowIndex(node) == column) {
                return true;
            }
        }
        return false;
    }
    //----------------------------------------------//

    private void fillChoiceBox(){
        whatTimeBox.getItems().add("19:00");
        whatTimeBox.getItems().add("17:00");
        whatTimeBox.getItems().add("15:00");
        whatTimeBox.getItems().add("13:00");
        whatTimeBox.getItems().add("11:00");
        whatTimeBox.getItems().add("9:00");
        List<String> hallNames = Screenings.HallNames();
        hallNames.forEach(s -> {
            whatTeremBox.getItems().add(s);
        });
        screeningIDS();
    }

    private void screeningIDS(){
        List<String> screeningIDs = Screenings.ScreeningIDs();
        screeningIDs.forEach(e->{
            whatScreeningID.getItems().add(e);
        });
        whatScreeningID.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    Screening temp = Screenings.getScreeingFromID(Integer.parseInt(whatScreeningID.getItems().get((Integer) t1)));
                    filmLabelNev.setText(temp.getFilm_nev());
                    whatTeremBox.getSelectionModel().select(temp.getTerem_nev());
                    whatTimeBox.getSelectionModel().select(temp.getNap());
                    whatDayPicker.setValue(temp.getDatum());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setToDefault(){
        whatTimeBox.getSelectionModel().select(-1);
        whatDayPicker.setValue(LocalDate.now());
        whatTeremBox.getSelectionModel().select(-1);
        filmLabelNev.setText("");
    }


    public void beszurVetites(ActionEvent actionEvent) {
        Screening temp = new Screening();
        String fl = filmLabelNev.getText();
        String wtb = whatTeremBox.getSelectionModel().getSelectedItem();
        String wtimeb = whatTimeBox.getSelectionModel().getSelectedItem();
        LocalDate wdp = whatDayPicker.getValue();
        String jegya = whatJegyar.getText();

        if(fl.isEmpty() || wtb == null || wtimeb == null || wdp == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Tölts ki minden szükséges mezőt");
            alert.showAndWait();
            return;
        }

        temp.setFilm_nev(fl);
        temp.setTerem_nev(wtb);
        temp.setDatum(wdp);
        temp.setNap(wtimeb);
        if (jegya == null) {
            temp.setJegyar(1500);
        } else {
            temp.setJegyar(Integer.parseInt(jegya));
        }
        Screenings.insert(temp);
        whatScreeningID.getItems().clear();
        screeningIDS();
        setToDefault();
    }

    public void vetitesTorol(ActionEvent actionEvent) {
        if(whatScreeningID.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Válassz ki melyik ID-t szeretnéd módositani!");
            alert.showAndWait();
            return;
        }
        Screening temp = new Screening();
        temp.setId(Integer.parseInt(whatScreeningID.getValue()));
        Screenings.delete(temp);
        whatScreeningID.getItems().clear();
        screeningIDS();
        setToDefault();
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