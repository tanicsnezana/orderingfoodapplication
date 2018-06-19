/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.ui.welcome;

import foodordering.orderer.welcome.OrdererWelcomeController;
import beans.Orderer;
import beans.Restaurant;
import db.DB;
import foodordering.ui.main.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import views.MealView;
import views.RestaurantView;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class WelcomeController implements Initializable {
    
     // button login
    @FXML private Button butttonLogin;

    // input fields for logging in
    @FXML private TextField usernameLogin;
    @FXML private TextField passwordLogin;
    
    // input fields for registration
    @FXML private TextField firstNameRegister;
    @FXML private TextField lastNameRegister;
    @FXML private TextField phoneNumberRegister;
    @FXML private TextField addressRegister;
    @FXML private TextField usernameRegister;
    @FXML private TextField passwordRegister;
    
    // needed restaurantsData for listing restaurants
    
    private final ObservableList<RestaurantView> restaurantsList = FXCollections.observableArrayList();
    private FilteredList<RestaurantView> restaurantsData;
    
    @FXML TableView<RestaurantView> restaurantsTable;
    @FXML TableColumn<RestaurantView, String> nameCol;
    @FXML TableColumn<RestaurantView, String> cuisineCol;
    @FXML TableColumn<RestaurantView, String> addressAndContactCol;
    @FXML TableColumn<RestaurantView, String> imageCol;
    @FXML TableColumn<RestaurantView, String> mealsCol;  
    @FXML TableColumn<RestaurantView, Button> markCol;
    @FXML TableColumn<RestaurantView, Button> commentsCol;
    
    // elemnts for searching
    
    @FXML ChoiceBox<String> searchBy;
    @FXML TextField searchHere;
    
    
    // needed restaurantsData for meals
    
    private final ObservableList<MealView> mealsList = FXCollections.observableArrayList();
    private FilteredList<MealView> mealsData;
    
    public WelcomeController() {
        
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
    
    
    @FXML
    private void handleLoginAction(ActionEvent event) throws IOException {
        String username = "";
        String password = "";
        String message = "";
        
        username = usernameLogin.getText();
        password = passwordLogin.getText();
        
        for(Orderer orderer: DB.users) {
            if(orderer.getUsername().equals(username) && orderer.getPassword().equals(password)){
                
                OrdererWelcomeController ordererController = new OrdererWelcomeController(orderer);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/foodordering/orderer/welcome/orderer-welcome.fxml"));
                loader.setController(ordererController);

                Stage stage = Main.getPrimaryStage();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Orderer");
                stage.show();
                
                return;
            }
   
        }
        
        Alert warnning = new Alert(Alert.AlertType.WARNING);
        warnning.setContentText("Wrong username or password!");
        warnning.showAndWait();
    }
    
    @FXML
    private void handleRegisterAction(ActionEvent event) {
        String firstName = "";
        String lastName = "";
        String phoneNumber = "";
        String address = "";
        String username = "";
        String password = "";
        
        firstName = firstNameRegister.getText();
        lastName = lastNameRegister.getText();
        phoneNumber = phoneNumberRegister.getText();
        address = addressRegister.getText();
        username = usernameRegister.getText();
        password = passwordRegister.getText();
        
        boolean warnningMessage = false;
        
        if(firstName.isEmpty() || firstName.equals("")) {
            warnningMessage = true;
        }
        
        if(lastName.isEmpty() || lastName.equals("")) {
            warnningMessage = true;
        }
        
        if(phoneNumber.isEmpty() || phoneNumber.equals("")) {
            warnningMessage = true;
        }
        
        if(address.isEmpty() || address.equals("")) {
            warnningMessage = true;
        }
        
        if(username.isEmpty() || username.equals("")) {
            warnningMessage = true;
        }
        
        if(password.isEmpty() || password.equals("")) {
            warnningMessage = true;
        }
        
        if(warnningMessage) {
            Alert warnning = new Alert(Alert.AlertType.WARNING);
            warnning.setContentText("All data are required");
            warnning.showAndWait();
            return;
        }
        
        for(Orderer orderer: DB.users) {
            if(orderer.getUsername().equals(username)) {
                Alert warnning = new Alert(Alert.AlertType.WARNING);
                warnning.setContentText("Username already exists");
                warnning.showAndWait();
                return;
            }
        }
        
        Orderer orderer = new Orderer();
        orderer.setName(firstName);
        orderer.setSurname(lastName);
        orderer.setPhoneNumber(phoneNumber);
        orderer.setAddress(address);
        orderer.setUsername(username);
        orderer.setPassword(password);
        DB.users.add(orderer);
        
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setContentText("Successfully registration!");
        success.showAndWait();
    }
    
    private void initSearching(){
        // adding options for searchinng - Cuisine and Name of restaurant
        
        searchBy.getItems().clear();
        searchBy.getItems().addAll("Name", "Cuisine");
        searchBy.setValue("Name");
        
        searchHere.setPromptText("Search Here!");
        searchHere.setOnKeyReleased(KeyEvent -> {
            switch(searchBy.getValue()) {
                case "Name": {
                   restaurantsData.setPredicate(p -> p.getName().toLowerCase().contains(searchHere.getText().toLowerCase().trim()));
                   break;
                }
                case "Cuisine": {
                   restaurantsData.setPredicate(p -> p.getCuisine().toLowerCase().contains(searchHere.getText().toLowerCase().trim()));
                   break;
                }
            }
        
        });
    }
    
    private void initColls(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cuisineCol.setCellValueFactory(new PropertyValueFactory<>("cuisine"));
        addressAndContactCol.setCellValueFactory(new PropertyValueFactory<>("addressAndContact"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        mealsCol.setCellValueFactory(new PropertyValueFactory<>("mealsButton"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("mark"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("commentsButton"));
    }
    
    private void loadData() {
        restaurantsList.clear();
        
        for(Restaurant restaurant: DB.restaurants) {
            String name = restaurant.getName();
            
            String description = restaurant.getDescription();
            
            
            String cuisine = restaurant.getCuisine();
            String paying = "";
            if(restaurant.isPayingByCard()) {
                paying += "Paying by Card ";
            }
            
            if(restaurant.isPayingByCash()) {
                if(restaurant.isPayingByCard()) {
                    paying += "| Paying by Cash";
                }
                else {
                    paying += "Paying by Cash";
                }
            }
            
            String otherInformation = restaurant.getAddress() + "\n\n"
                                      + restaurant.getPhoneNumber() + "\n\n"
                                      + restaurant.getVisitingHours();
                                    
     
            String mark = String.valueOf(restaurant.getMark());
            
            RestaurantView restaurantView = new RestaurantView(name, cuisine, otherInformation, restaurant.getImageSrc(), restaurant.getMeals(), mark, restaurant.getComments());
            restaurantsList.add(restaurantView);
        }
        
     restaurantsData = new FilteredList<>(restaurantsList);
     restaurantsTable.setItems(restaurantsData);
    }
    

        
}
