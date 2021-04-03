package hu.alkfejl;

import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
/*
    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hu/alkfejl/view/main_window.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
private static Stage stage;

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        App.loadFXML("/fxml/main_window.fxml");
        stage.show();
    }

    public static FXMLLoader loadFXML(String fxml){
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = null;
        try {
            Parent root  = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        return loader;
    }
    public static void main(String[] args) {



         RoomDAO roomDAO = new RoomDAOImpl();
         List<Room> rl = roomDAO.findAll();
        System.out.println(rl);

        launch();
    }

}