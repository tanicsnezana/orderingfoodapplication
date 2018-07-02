/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.orderer.restaurants.addcomment;

import beans.Comment;
import beans.Orderer;
import beans.Restaurant;
import foodordering.orderer.welcome.OrdererWelcomeController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class AddCommentController implements Initializable {
    
    Orderer orderer;
    Restaurant resturant;

    @FXML private TextField comment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public AddCommentController(Orderer orderer, Restaurant restaurant) {
        this.orderer = orderer;
        this.resturant = restaurant;
    }
    
    @FXML
    private void handleAddComment(ActionEvent event) {
        String commentContent = "";
        
        commentContent = comment.getText();
        
        if(resturant.getComments() == null) {
            resturant.setComments(new LinkedList<>());
        }
        
        Comment _comment = new Comment();
        _comment.setComment(commentContent);
        _comment.setOrderer(orderer);
        
        resturant.getComments().add(_comment);
        
        comment.clear();
        
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setContentText("Successfully added comment!");
        info.showAndWait();
        
    }
    
    @FXML 
    private void handleBack(ActionEvent event) {
        try {

            OrdererWelcomeController ordererWelcomeController = new OrdererWelcomeController(orderer);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/foodordering/orderer/welcome/orderer-welcome.fxml"));
            loader.setController(ordererWelcomeController);

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("orderer");
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(OrdererWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
