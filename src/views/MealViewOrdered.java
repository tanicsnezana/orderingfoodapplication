/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Orderer;
import beans.Restaurant;
import eventhandlers.EventHandlerMeal;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
public class MealViewOrdered extends MealView {
    private final Orderer orderer;
    private final Button addComment;

    public MealViewOrdered(String name, String kindAndCategory, String imageSrc, Restaurant restaurant, String price, Orderer orderer) {
        this.restaurant = restaurant;
        
        this.name = name;
        
        this.price = price;
        
        this.kindAndCategory = kindAndCategory;
        
        this.image = new ImageView(new Image(imageSrc));
        
        this.orderer = orderer;
        
        this.addComment = new Button("Add");
    }
    
    public Orderer getOrderer() {
        return orderer;
    }
    
    public Button getAddComment() {
        return addComment;
    }
       
}
