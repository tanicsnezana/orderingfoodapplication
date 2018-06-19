/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import eventhandlers.EventHandlerRestaurant;
import beans.Comment;
import beans.Meal;
import beans.Restaurant;
import foodordering.restaurant.comments.RestaurantCommentsController;

import foodordering.restaurant.meals.MealsController;
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
public class RestaurantView {

    protected String name;
    protected String cuisine;
    protected String addressAndContact;
    protected ImageView image;
    protected Button mealsButton;
    protected String mark;
    protected List<Meal> meals;
    protected Button commentsButton;
    
    public RestaurantView() {
        
    }

    public RestaurantView(String name, String cuisine, String addressAndContact, String imageSrc, List<Meal> meals, String mark, List<Comment> comments) {
        
        this.name = name;

        this.cuisine = cuisine;

        this.addressAndContact = addressAndContact;
        
        this.meals = meals;
                
        this.mealsButton = new Button("Show");   
        this.mealsButton.setOnAction(new EventHandlerRestaurant(findRestaurant(name, this)) {
            
            @Override
            public void handle(Event event) {
                try {
                    MealsController mealsController = null;
                    mealsController = new MealsController(restaurant);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/restaurant/meals/meals.fxml"));
                    loader.setController(mealsController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Orderer");
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
                    RestaurantCommentsController restaurantCommentsController = new RestaurantCommentsController(restaurant);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/restaurant/comments/restaurant-comments.fxml"));
                    loader.setController(restaurantCommentsController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Restaurant Comments");
                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(RestaurantView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        image = new ImageView(new Image(imageSrc));
        
        this.mark = mark;
    }

    protected Restaurant findRestaurant(String name, RestaurantView restaurantView){
        for(Restaurant res: db.DB.restaurants) {
            if(res.getName().equals(name)){
                return res;
            }
        }
        
        return null;
    }
    
    public Button getCommentsButton() {
        return commentsButton;
    }
    
    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getAddressAndContact() {
        return addressAndContact;
    }

    public ImageView getImage() {
        return image;
    }
    
    public Button getMealsButton(){
        return mealsButton;
    }
    
    public String getMark(){
        return mark;
    }
}
