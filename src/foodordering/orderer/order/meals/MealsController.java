/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.orderer.order.meals;

import beans.Meal;
import beans.Order;
import beans.Orderer;
import beans.Restaurant;
import foodordering.orderer.welcome.OrdererWelcomeController;
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
import views.MealViewOrdered;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class MealsController implements Initializable {
    private Restaurant restuarant;
    private Orderer orderer;
    private Order order;
    
    private final ObservableList<MealViewOrdered> mealsList = FXCollections.observableArrayList();
    
    @FXML TableView<MealViewOrdered> mealsTable;
    
    @FXML TableColumn<MealViewOrdered, String> nameCol;
    @FXML TableColumn<MealViewOrdered, String> kindAndCategoryCol;
    @FXML TableColumn<MealViewOrdered, String> imageCol;
    @FXML TableColumn<MealViewOrdered, String> priceCol;
    @FXML TableColumn<MealViewOrdered, String> addCommentCol;
    
    public MealsController() {
        
    }
    
    public MealsController(Orderer orderer, Restaurant restaurant, Order order) {
        this.orderer = orderer;
        this.restuarant = restaurant;
        this.order = order;
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        kindAndCategoryCol.setCellValueFactory(new PropertyValueFactory<>("kindAndCategory"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addCommentCol.setCellValueFactory(new PropertyValueFactory<>("addCommentButton"));
    }
    
    private void loadData() {
        mealsList.clear();
        
        order.getMeals().stream().map((meal) -> {
            String name = meal.getName();
            String kindAndCategory = meal.getKind() + "\n\n"  + meal.getCategory();
            String imageSrc = meal.getImageSrc();
            String price = Double.toString(meal.getPrice());
            MealViewOrdered mealViewOrdered = new MealViewOrdered(name, kindAndCategory, imageSrc, restuarant, price, orderer);
            return mealViewOrdered;            
        }).forEach((mealViewOrdered) -> {
            mealsList.add(mealViewOrdered);
        });
        
        mealsTable.setItems(mealsList);
    }
    
    @FXML
    private void handleBack() {
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
