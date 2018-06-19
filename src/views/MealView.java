/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import eventhandlers.EventHandlerMeal;
import beans.Meal;
import beans.Restaurant;
import foodordering.meal.comments.MealsCommentsController;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author stan
 */
public class MealView {
    protected String name;
    protected String kindAndCategory;
    protected Button otherInformationButton;
    protected ImageView image;
    protected Button commentsButton;
    protected Restaurant restaurant;
    protected String price;
    
    public MealView() {
        
    }
    
    public MealView(String name, String description, String kindAndCategory, String ingredients, String imageSrc, String comments, Restaurant restaurant, String price) {
        this.restaurant = restaurant;
        
        this.name = name;
        
        this.price = price;
        
        String otherInformation = description + "\n\n" + ingredients;
        
        this.otherInformationButton = new Button("Show");
        this.otherInformationButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text(otherInformation));
                Scene dialogScene = new Scene(dialogVbox, 500, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        
        this.kindAndCategory = kindAndCategory;
        
        this.image = new ImageView(new Image(imageSrc));
        
        this.commentsButton = new Button("Show");
        this.commentsButton.setOnAction(new EventHandlerMeal(findMeal(this.name), restaurant) {
            
            @Override
            public void handle(Event event) {
                try {
                    MealsCommentsController mealsCommentController;
                    mealsCommentController = new MealsCommentsController(meal, restaurant);
                   
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/foodordering/meal/comments/meals-comments.fxml"));
                    loader.setController(mealsCommentController);

                    Stage stage = Main.getPrimaryStage();
                    Scene scene;

                    scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.setTitle("Meals Comments");
                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(RestaurantView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
    
    protected Meal findMeal(String name) {
        for(Meal meal: restaurant.getMeals()) {
            if(meal.getName().equals(name)) {
                return meal;
            }
        }
        
        return null;
    }
    
    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Button getOtherInformationButton(){
        return otherInformationButton;
    }

    public String getKindAndCategory() {
        return kindAndCategory;
    }

    public ImageView getImage() {
        return image;
    }

    public Button getCommentsButton() {
        return commentsButton;
    }
    
    
}
