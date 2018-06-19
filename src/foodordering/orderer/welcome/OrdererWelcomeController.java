/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodordering.orderer.welcome;

import beans.Meal;
import beans.Order;
import beans.Orderer;
import db.DB;
import foodordering.ui.main.Main;
import foodordering.ui.welcome.WelcomeController;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import views.CartView;
import views.OrderView;
import views.RestaurantView;
import views.RestaurantViewOrderer;

/**
 * FXML Controller class
 *
 * @author stan
 */
public class OrdererWelcomeController implements Initializable {
    
    private Orderer orderer;
    
    // cart
    
    private final ObservableList<CartView> cartList = FXCollections.observableArrayList();
    
    @FXML private TextField totalPrice;
    
    @FXML private Button orderFood;
    
    @FXML TableView<CartView> cartTable;
    @FXML TableColumn<CartView, String> mealNameCol;
    @FXML TableColumn<CartView, String> mealImageCol;
    @FXML TableColumn<CartView, String> priceCol;
    
    
    // orders 
    private final ObservableList<OrderView> ordersList = FXCollections.observableArrayList();
    
    @FXML TableView<OrderView> ordersTable;
    @FXML TableColumn<OrderView, String> restaurantImageCol;
    @FXML TableColumn<OrderView, String> restaurantNameCol;
    @FXML TableColumn<OrderView, Button> showMealsCol;
    @FXML TableColumn<OrderView, Button> addCommentCol;
    
    // restaurants
    
    private final ObservableList<RestaurantView> restaurantsList = FXCollections.observableArrayList();
    private FilteredList<RestaurantView> restaurantsData;
    
    // data for changing password
    
    @FXML private TextField oldPassword;
    @FXML private TextField newPassword;
    @FXML private TextField confirmNewPassword;
    
    @FXML TableView<RestaurantView> restaurantsTable;
    @FXML TableColumn<RestaurantViewOrderer, String> nameCol;
    @FXML TableColumn<RestaurantViewOrderer, String> cuisineCol;
    @FXML TableColumn<RestaurantViewOrderer, String> addressAndContactCol;
    @FXML TableColumn<RestaurantViewOrderer, String> imageCol;
    @FXML TableColumn<RestaurantViewOrderer, Button> mealsCol;  
    @FXML TableColumn<RestaurantViewOrderer, String> markCol;
    @FXML TableColumn<RestaurantViewOrderer, Button> commentsCol;
    @FXML TableColumn<RestaurantViewOrderer, Button> orderButtonCol;
   
    
    // elemnts for searching
    
    @FXML ChoiceBox<String> searchBy;
    @FXML TextField searchHere;
    
    // elements for choosing paying way
    
    @FXML ChoiceBox<String> paying;
    
    // user's personal data
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phoneNumber;
    @FXML private TextField address;
    @FXML private TextField username;
    
    @FXML private Button editAndSave;
    
    @FXML private ImageView profilePhoto;
    @FXML private Image image;
    
    public OrdererWelcomeController() {
        
    }
    
    public OrdererWelcomeController(Orderer orderer) {
        this.orderer = orderer;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initSearching();
        
        initCollsRestaurantTable();
        loadDataRestaurantTable();
        
        
        if(orderer.getOrders() != null && !orderer.getOrders().isEmpty()){
            initCollsOrderTable();
            loadDataOrderTable();
        }
        
        if(orderer.getCart() != null && orderer.getCart().getOrder() != null && orderer.getCart().getOrder().getMeals() != null) {
            initCollsCartTable();
            loadDataCartTable();
            calculatePrice();
        }
        
  
        initPersonalData();
        
        initPaying();
    }    
    
    private void initPersonalData() {
        firstName.setText(orderer.getName());
        firstName.setEditable(false);
        
        lastName.setText(orderer.getSurname());
        lastName.setEditable(false);
        
        phoneNumber.setText(orderer.getPhoneNumber());
        phoneNumber.setEditable(false);
        
        address.setText(orderer.getAddress());
        address.setEditable(false);
        
        username.setText(orderer.getUsername());
        username.setEditable(false);
        
        if(orderer.getImageSrc() != null) {
            profilePhoto = new ImageView();
            image = new Image(orderer.getImageSrc());
            profilePhoto.setImage(image);
        }
        else {
            profilePhoto = new ImageView();
            image = new Image("/users/profile.png");
            profilePhoto.setImage(image);
        }
    }
    
    private void calculatePrice() {
        totalPrice.setEditable(false);

        double price = 0;

        price = orderer.getCart().getOrder().getMeals().stream().map((meal) -> meal.getPrice()).reduce(price, (accumulator, _item) -> accumulator + _item);

        totalPrice.setText(String.valueOf(price));
    }
    
    private void initPaying() {
        paying.getItems().clear();
        
        if(orderer.getCart() != null && orderer.getCart().getOrder() != null && orderer.getCart().getOrder().getMeals() != null) {
            String option = "";
            
            
            if(orderer.getCart().getOrder().getRestaurant().isPayingByCard()) {
                option += "Paying by card";
                paying.getItems().add(option);
                paying.setValue(option);
            }
            
            if(orderer.getCart().getOrder().getRestaurant().isPayingByCash()) {
                paying.getItems().add(option);
                if(paying.getValue() == null) {
                    paying.setValue(option);
                }
            }
        }
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
    
    private void initCollsCartTable() {
        mealImageCol.setCellValueFactory(new PropertyValueFactory<>("mealImage"));
        mealNameCol.setCellValueFactory(new PropertyValueFactory<>("mealName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void loadDataCartTable() {
       cartList.clear();
       
       for(Meal meal: orderer.getCart().getOrder().getMeals()) {
           String mealName = meal.getName();
           String mealImageSrc = meal.getImageSrc();
           String mealPrice = String.valueOf(meal.getPrice());
           
           CartView cartView = new CartView(mealName, mealImageSrc, mealPrice);
           cartList.add(cartView);
       }
       
       cartTable.setItems(cartList);
    }
    
    private void initCollsOrderTable() {
        restaurantImageCol.setCellValueFactory(new PropertyValueFactory<>("restaurantImage"));
        restaurantNameCol.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        showMealsCol.setCellValueFactory(new PropertyValueFactory<>("showMeals"));
        addCommentCol.setCellValueFactory(new PropertyValueFactory<>("addComment"));
    }
    
    private void loadDataOrderTable() {
        ordersList.clear();
        
        orderer.getOrders().stream().map((order) -> {
            String imageSrc = order.getRestaurant().getImageSrc();
            String restaurantName = order.getRestaurant().getName();
            OrderView orderView = new OrderView(imageSrc, restaurantName, order.getMeals(), orderer, order);
            return orderView;            
        }).forEach((orderView) -> {
            ordersList.add(orderView);
        });
        
        ordersTable.setItems(ordersList);
    }
    
    private void initCollsRestaurantTable(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cuisineCol.setCellValueFactory(new PropertyValueFactory<>("cuisine"));
        addressAndContactCol.setCellValueFactory(new PropertyValueFactory<>("addressAndContact"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        mealsCol.setCellValueFactory(new PropertyValueFactory<>("mealsButton"));
        markCol.setCellValueFactory(new PropertyValueFactory<>("mark"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("commentsButton"));
        orderButtonCol.setCellValueFactory(new PropertyValueFactory<>("orderButton"));
    }
    
    private void loadDataRestaurantTable() {
        restaurantsList.clear();
        
        DB.restaurants.stream().map((restaurant) -> {
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
            RestaurantViewOrderer restaurantView = new RestaurantViewOrderer(name, cuisine, otherInformation, restaurant.getImageSrc(), restaurant.getMeals(), mark, restaurant.getComments(), orderer);
            return restaurantView;
        }).forEach((restaurantView) -> {
            restaurantsList.add(restaurantView);
        });
        
        restaurantsData = new FilteredList<>(restaurantsList);
        restaurantsTable.setItems(restaurantsData);
    }
    
    @FXML
    private void handleOrder() {
        
        Order order = orderer.getCart().getOrder();
        
        String restaurantName = order.getRestaurant().getName();
        String restaurantImageSrc = order.getRestaurant().getImageSrc();
        
        OrderView orderView = new OrderView(restaurantImageSrc, restaurantName, order.getMeals(), orderer, order);
        
        
        if(ordersList.isEmpty()) {
            initCollsOrderTable();
        }
        
        if(orderer.getOrders() == null) {
            orderer.setOrders(new LinkedList<>());
        }
        
        orderer.getOrders().add(order);
        
        ordersList.add(orderView);
        ordersTable.setItems(ordersList);
        
        cartList.clear();
        cartTable.setItems(cartList);
        
        totalPrice.setText("");
        
        orderer.setCart(null);
        
    }
    
    @FXML
    private void handleLogOut(ActionEvent event) {
        
        try {
            WelcomeController welcomeController = new WelcomeController();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/foodordering/ui/welcome/welcome.fxml"));
            loader.setController(welcomeController);
            
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Food Ordering");
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(OrdererWelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void handleChangePassword(ActionEvent event) {
        
        String _oldPassword = "";
        _oldPassword = oldPassword.getText();
        String _newPassword = "";
        _newPassword = newPassword.getText();
        String _confirmNewPassword = "";
        _confirmNewPassword = confirmNewPassword.getText();
        
        boolean warnningMessage = false;
        
        if(_oldPassword.isEmpty() || _oldPassword.equals("")) {
            warnningMessage = true;
        }
        
        if(_newPassword.isEmpty() || _newPassword.equals("")) {
            warnningMessage = true;
        }
        
        if(_confirmNewPassword.isEmpty() || _confirmNewPassword.equals("")){
            warnningMessage = true;
        }
        
        if(warnningMessage) {
            Alert warnning = new Alert(Alert.AlertType.WARNING);
            warnning.setContentText("All data are required");
            warnning.showAndWait();
            return;
        }
        
        if(!orderer.getPassword().equals(_oldPassword)) {
            Alert warnning = new Alert(Alert.AlertType.WARNING);
            warnning.setContentText("Password isn't correct!");
            warnning.showAndWait();
            return;
        }
        
        if(!_newPassword.equals(_confirmNewPassword)) {
            Alert warnning = new Alert(Alert.AlertType.WARNING);
            warnning.setContentText("Passwords aren't matching!");
            warnning.showAndWait();
            return;
        }
        
        for(Orderer o: DB.users) {
            if(o.equals(orderer)) {
                o.setPassword(_newPassword);
                
                newPassword.clear();
                oldPassword.clear();
                confirmNewPassword.clear();
     
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Successfully changed password!");
                info.showAndWait();
                return;
            }
        }
    }
    
    @FXML
    private void handleEditAndSavePersonalData(ActionEvent event) {
        switch (editAndSave.getText()) {
            case "Edit":
                firstName.setEditable(true);
                lastName.setEditable(true);
                phoneNumber.setEditable(true);
                address.setEditable(true);
                username.setEditable(true);
                editAndSave.setText("Save");
                break;
            case "Save":
                String _firstName = firstName.getText();
                String _lastName = lastName.getText();
                String _phoneNumber = phoneNumber.getText();
                String _address = address.getText();
                String _username = username.getText();
                for(Orderer o: DB.users) {
                    if(o.equals(orderer)) {
                        o.setAddress(_address);
                        o.setName(_firstName);
                        o.setSurname(_lastName);
                        o.setPhoneNumber(_phoneNumber);
                        o.setUsername(_username);
                        
                        orderer = o;
                        initPersonalData();
                        
                        firstName.setEditable(false);
                        lastName.setEditable(false);
                        phoneNumber.setEditable(false);
                        address.setEditable(false);
                        username.setEditable(false);
                        
                        editAndSave.setText("Edit");
                        
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setContentText("Successfully edited personal data!");
                        info.showAndWait();
                        return;
                    }
                }   break;
        }
    }
}
