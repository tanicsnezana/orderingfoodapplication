/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.meal.comments;

import beans.Comment;
import beans.Meal;
import beans.Orderer;
import beans.Restaurant;
import foodordering.orderer.welcome.OrdererWelcomeController;
import foodordering.restaurant.meals.MealsController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import views.CommentView;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class MealsCommentsController implements Initializable {
    
    private Orderer orderer = null;
    private Meal meal;
    private Restaurant restaurant;
    
    private final ObservableList<CommentView> commentsList = FXCollections.observableArrayList();
    
    @FXML TableView<CommentView> commentsTable;
    
    @FXML TableColumn<CommentView, String> usernameCol;
    @FXML TableColumn<CommentView, String> commentCol;
    
    public MealsCommentsController() {
        
    }
    
    public MealsCommentsController(Meal meal, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.meal = meal;
    }
    
    public MealsCommentsController(Meal meal, Restaurant restaurant, Orderer orderer) {
        this.restaurant = restaurant;
        this.meal = meal;
        this.orderer = orderer;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColls();
        loadData();
    }  
    
    private void initColls() {
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }
    
    private void loadData() {
        commentsList.clear();
        
        for(Comment comment : meal.getComments()) {
            String username = comment.getOrderer().getUsername();
            String comm = comment.getComment();
            
            CommentView commentView = new CommentView(username, comm);
            
            commentsList.add(commentView);
        }
        
        commentsTable.setItems(commentsList);
    }
    
     @FXML
    private void handleBack() {
        try {
            if(orderer == null) {
                MealsController mealsController = new MealsController(restaurant);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/foodordering/restaurant/meals/meals.fxml"));
                loader.setController(mealsController);

                Stage stage = Main.getPrimaryStage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Meals");
                stage.show();
            }
            else {
                foodordering.orderer.restaurant.meals.MealsController mealsController = new foodordering.orderer.restaurant.meals.MealsController(restaurant, orderer);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/foodordering/orderer/restaurant/meals/meals.fxml"));
                loader.setController(mealsController);

                Stage stage = Main.getPrimaryStage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Meals");
                stage.show();
            }

            return;
        } catch (IOException ex) {
            Logger.getLogger(OrdererWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
