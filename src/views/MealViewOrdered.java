/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Order;
import beans.Orderer;
import beans.Restaurant;
import eventhandlers.EventHandlerMeal;
import foodordering.orderer.meals.addcomment.AddCommentController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author stan
 */
public class MealViewOrdered extends MealView {
    private final Orderer orderer;
    private final Button addComment;
    private final Order order;

    public MealViewOrdered(String name, String kindAndCategory, String imageSrc, Restaurant restaurant, String price, Orderer orderer, Order order) {
        this.restaurant = restaurant;
        
        this.order = order;
        
        this.name = name;
        
        this.price = price;
        
        this.kindAndCategory = kindAndCategory;
        
        this.image = new ImageView(new Image(imageSrc));
        
        this.orderer = orderer;
        
        this.addComment = new Button("Add");
         this.addComment.setOnAction(new EventHandlerMeal(findMeal(name), restaurant) {

            @Override
            public void handle(Event event) {
                try {
                    AddCommentController addCommentController = new AddCommentController(orderer, meal, restaurant, order);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/orderer/meals/addcomment/addcomment.fxml"));
                    loader.setController(addCommentController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;
                    scene = new Scene(loader.load());

                    stage.setScene(scene);
                    stage.setTitle("Add Comment");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
    }
    
    public Orderer getOrderer() {
        return orderer;
    }
    
    public Button getAddComment() {
        return addComment;
    }
       
}
