package hu.alkfejl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import hu.alkfejl.dao.DBConnector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;


/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        App.loadFXML("/fxml/primary");
    }

    public static FXMLLoader loadFXML(String fxml){
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        Scene scene = null;
        try{
            Parent root = loader.load();
            scene = new Scene(root,800,600);
            scene.getStylesheets().add("/css/style.css");

            root.setOnMousePressed(event->{
                root.setOnMouseDragged(e -> {
                    stage.setX(e.getScreenX() - event.getSceneX());
                    stage.setY(e.getScreenY() - event.getSceneY());
                });
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        return loader;
    }

    public static void close(){
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean regex(String patt, String match){
        Pattern pattern = Pattern.compile(patt, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(match);
        return matcher.find();
    }

}
