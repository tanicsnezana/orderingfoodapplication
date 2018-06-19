/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.orderer.order;

import beans.Orderer;
import beans.Restaurant;
import foodordering.orderer.welcome.OrdererWelcomeController;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import views.MealViewOrder;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class OrderFoodController implements Initializable {
    private Restaurant restaurant;
    private Orderer orderer;
    
    
    private final ObservableList<MealViewOrder> mealsList = FXCollections.observableArrayList();
    private FilteredList<MealViewOrder> mealsData;
    
    @FXML TableView<MealViewOrder> mealsTable;
    
    @FXML TableColumn<MealViewOrder, String> nameCol;
    @FXML TableColumn<MealViewOrder, String> kindAndCategoryCol;
    @FXML TableColumn<MealViewOrder, String> imageCol;
    @FXML TableColumn<MealViewOrder, String> otherInformationCol;
    @FXML TableColumn<MealViewOrder, String> addMealCol;
    @FXML TableColumn<MealViewOrder, String> priceCol;
    
    @FXML ChoiceBox<String> searchBy;
    @FXML TextField searchHere;
    
    public OrderFoodController() {
        
    }
    
    public OrderFoodController(Restaurant restaurant, Orderer orderer) {
        this.restaurant = restaurant;
        this.orderer = orderer;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initSearching();
        initColls();
        loadData();
    }    
    
    private void initSearching(){
        searchBy.getItems().clear();
        searchBy.getItems().addAll("Name", "Category");
        searchBy.setValue("Name");
        
        searchHere.setPromptText("Search Here!");
        searchHere.setOnKeyReleased(KeyEvent -> {
            switch(searchBy.getValue()) {
                case "Name": {
                   mealsData.setPredicate(p -> p.getName().toLowerCase().contains(searchHere.getText().toLowerCase().trim()));
                   break;
                }
                case "Category": {
                   mealsData.setPredicate(p -> p.getKindAndCategory().toLowerCase().contains(searchHere.getText().toLowerCase().trim()));
                   break;
                }
            }
        
        });
    }
    
    private void initColls(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        kindAndCategoryCol.setCellValueFactory(new PropertyValueFactory<>("kindAndCategory"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        otherInformationCol.setCellValueFactory(new PropertyValueFactory<>("otherInformationButton"));
        addMealCol.setCellValueFactory(new PropertyValueFactory<>("addMeal"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void loadData() {
        mealsList.clear();
        
        restaurant.getMeals().stream().map((meal) -> {
            String ingredients = makeIngredients(meal.getIngredients());
            String comments = "";
            String kindAndCategory = meal.getKind() + "\n\n" + meal.getCategory().toString().toLowerCase();
            String price = String.valueOf(meal.getPrice());
            MealViewOrder mealView = new MealViewOrder(meal.getName(), meal.getDescription(), kindAndCategory, ingredients,  meal.getImageSrc(), comments, orderer, restaurant, price);
            return mealView;
        }).forEach((mealView) -> {
            mealsList.add(mealView);
        });
        
        mealsData = new FilteredList<>(mealsList);
        mealsTable.setItems(mealsData);
    }
    
    private String makeIngredients(List<String> list) {
        StringBuilder ingredients = new StringBuilder("");
        
        for(int i = 0; i < list.size(); i++) {
            if(i == list.size() - 1) {
                ingredients.append(list.get(i));
            }
            else {
                ingredients.append(list.get(i) + ", ");
            }
        }
        return ingredients.toString();
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {

            OrdererWelcomeController ordererWelcomeController = new OrdererWelcomeController(orderer);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/foodordering/orderer/welcome/orderer-welcome.fxml"));
            loader.setController(ordererWelcomeController);

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Orderer");
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(OrdererWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
