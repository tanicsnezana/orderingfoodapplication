/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.ui.main;

import foodordering.ui.welcome.WelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author stan
 */
public class Main extends Application {
    private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeController welcomeController = new WelcomeController();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/foodordering/ui/welcome/welcome.fxml"));
        loader.setController(welcomeController);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Food Ordering");
        stage.show();
        
        setPrimaryStage(stage);

        return;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
