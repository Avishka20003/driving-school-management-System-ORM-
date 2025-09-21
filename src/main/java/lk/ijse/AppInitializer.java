package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws IOException {
   Parent load = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml")).getClass();
           new Stage = new Stage();


    }
}