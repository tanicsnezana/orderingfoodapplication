/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Cart;
import eventhandlers.EventHandlerRestaurant;
import beans.Comment;
import beans.Meal;
import beans.Order;
import beans.Orderer;
import foodordering.orderer.order.OrderFoodController;
import foodordering.restaurant.comments.RestaurantCommentsController;
import foodordering.orderer.restaurant.meals.MealsController;
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
public class RestaurantViewOrderer extends RestaurantView {
    
    private final Orderer orderer;
    private final Button orderButton;
    
    public RestaurantViewOrderer(String name, String cuisine, String addressAndContact, String imageSrc, List<Meal> meals, String mark, List<Comment> comments, Orderer orderer) {
        this.name = name;

        this.cuisine = cuisine;

        this.addressAndContact = addressAndContact;
        
        this.meals = meals;
        
        this.orderButton = new Button("Order");
        this.orderButton.setOnAction(new EventHandlerRestaurant(findRestaurant(name, this)) {
            
            @Override
            public void handle(Event event) {
                try {
                    Cart cart = new Cart();
                    Order order = new Order();
                    
                    order.setOrderer(orderer);
                    order.setRestaurant(restaurant);
                    
                    cart.setOrder(order);
                    
                    orderer.setCart(cart);
                    
                    OrderFoodController orderFoodController = new OrderFoodController(restaurant, orderer);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/orderer/order/order-food.fxml"));
                    loader.setController(orderFoodController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Order Food");
                    stage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(RestaurantView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
                
        this.mealsButton = new Button("Show");
        this.mealsButton.setOnAction(new EventHandlerRestaurant(findRestaurant(name, this)) {
            
            @Override
            public void handle(Event event) {
                try {
                    MealsController mealsController = new MealsController(restaurant, orderer);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/orderer/restaurant/meals/meals.fxml"));
                    loader.setController(mealsController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Meals");
                    stage.show();

                    return;
                } catch (IOException ex) {
                    Logger.getLogger(RestaurantView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        this.commentsButton = new Button("Show");
        this.commentsButton.setOnAction(new EventHandlerRestaurant(findRestaurant(name, this)) {
            
            @Override
            public void handle(Event event) {
                try {
                    RestaurantCommentsController restaurantCommentsController = new RestaurantCommentsController(restaurant, orderer);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/restaurant/comments/restaurant-comments.fxml"));
                    loader.setController(restaurantCommentsController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Restaurant Comments");
                    stage.show();

                    return;
                } catch (IOException ex) {
                    Logger.getLogger(RestaurantView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
       

        image = new ImageView(new Image(imageSrc));
        
        this.mark = mark;
        
        this.orderer = orderer;
    }
    
    public Orderer getOrderer() {
        return orderer;
    }
    
    public Button getOrderButton() {
        return orderButton;
    }
}
