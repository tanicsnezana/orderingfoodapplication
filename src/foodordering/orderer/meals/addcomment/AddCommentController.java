/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.orderer.meals.addcomment;

import beans.Comment;
import beans.Meal;
import beans.Order;
import beans.Orderer;
import beans.Restaurant;
import foodordering.orderer.order.meals.MealsController;
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
    Meal meal;
    Restaurant restaurant;
    Order order;
    
    @FXML private TextField comment;
    
    public AddCommentController(Orderer orderer, Meal meal, Restaurant restaurant, Order order) {
        this.orderer = orderer;
        this.meal = meal;
        this.restaurant = restaurant;
        this.order = order;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void handleAddComment(ActionEvent event) {
        String commentContent = "";
        
        commentContent = comment.getText();
        
        if(meal.getComments() == null) {
            meal.setComments(new LinkedList<>());
        }
        
        Comment _comment = new Comment();
        _comment.setComment(commentContent);
        _comment.setOrderer(orderer);
        
        meal.getComments().add(_comment);
        
        comment.clear();
        
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setContentText("Successfully added comment!");
        info.showAndWait();
    }
    
    @FXML 
    private void handleBack(ActionEvent event) {
        try {

            MealsController mealsController = new MealsController(orderer, restaurant, order);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/foodordering/orderer/order/meals/meals.fxml"));
            loader.setController(mealsController);

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Meals");
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(OrdererWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
