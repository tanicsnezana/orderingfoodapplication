/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Cart;
import beans.Order;
import beans.Orderer;
import beans.Restaurant;
import eventhandlers.EventHandlerMeal;
import foodordering.meal.comments.MealsCommentsController;
import foodordering.orderer.order.OrderFoodController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class MealViewOrder extends MealView {

    private final Orderer orderer;
    private final Button addMeal;

    public MealViewOrder(String name, String description, String kindAndCategory, String ingredients, String imageSrc, String comments, Orderer orderer, Restaurant restaurant, String price) {
        this.restaurant = restaurant;

        this.name = name;

        this.price = price;

        this.addMeal = new Button("Add");
        this.addMeal.setOnAction(new EventHandlerMeal(findMeal(this.name), restaurant) {

            @Override
            public void handle(Event event) {
                Cart cart = orderer.getCart();
                if (cart.getOrder() == null) {
                    Order order = new Order();
                    order.setMeals(new LinkedList<>());
                    order.getMeals().add(meal);
                } else if (cart.getOrder().getMeals() == null) {

                    cart.getOrder().setMeals(new LinkedList<>());

                    cart.getOrder().getMeals().add(meal);
                } else {
                    cart.getOrder().getMeals().add(meal);
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Successfully added");
                info.showAndWait();
            }

        });

        String otherInformation;
        otherInformation = description + "\n\n" + ingredients;

        this.otherInformationButton = new Button("Show");
        this.otherInformationButton.setOnAction((ActionEvent event) -> {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text(otherInformation));
            Scene dialogScene = new Scene(dialogVbox, 500, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        });

        this.kindAndCategory = kindAndCategory;

        this.image = new ImageView(new Image(imageSrc));

        this.orderer = orderer;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public Button getAddMeal() {
        return addMeal;
    }

}
