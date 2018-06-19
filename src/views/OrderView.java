/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Meal;
import beans.Order;
import beans.Orderer;
import beans.Restaurant;
import eventhandlers.EventHandlerRestaurant;
import foodordering.orderer.order.meals.MealsController;
import foodordering.orderer.restaurants.addcomment.AddCommentController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
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
public class OrderView {
    private final ImageView restaurantImage;
    private final String restaurantName;
    private final Button showMeals;
    private final Button addComment;
    private final Orderer orderer;
    private final Order order;
    
    public OrderView(String imageSrc, String restaurantName, List<Meal> meals, Orderer orderer, Order order) {
        
        this.order = order;
        
        this.orderer = orderer;
        
        restaurantImage = new ImageView(new Image(imageSrc));
        
        this.restaurantName = restaurantName;
        
        this.showMeals = new Button("Show");
        this.showMeals.setOnAction(new EventHandlerRestaurant(findRestaurant(restaurantName)) {

            @Override
            public void handle(Event event) {
                try {
                    MealsController mealsController = new MealsController(orderer, restaurant, order);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/orderer/order/meals/meals.fxml"));
                    loader.setController(mealsController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;
                    scene = new Scene(loader.load());

                    stage.setScene(scene);
                    stage.setTitle("Orderer");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }
        
        });
        
        this.addComment = new Button("Add Comment");
        this.addComment.setOnAction(new EventHandlerRestaurant(findRestaurant(restaurantName)) {

            @Override
            public void handle(Event event) {
                try {
                    AddCommentController addCommentController = new AddCommentController(orderer, restaurant);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/orderer/restaurants/addcomment/addcomment.fxml"));
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
    
    private Restaurant findRestaurant(String name){
        for(Restaurant res: db.DB.restaurants) {
            if(res.getName().equals(name)){
                return res;
            }
        }
        
        return null;
    }

    public ImageView getRestaurantImage() {
        return restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Button getShowMeals() {
        return showMeals;
    }

    public Button getAddComment() {
        return addComment;
    }
   
}
