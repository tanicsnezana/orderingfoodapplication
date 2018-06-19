/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author stan
 */
public class CartView {
    private final String mealName;
    private final ImageView mealImage;
    private final String price;

 
    public CartView(String mealName, String imageSrc, String price) {
        this.mealName = mealName;
        
        this.mealImage = new ImageView(new Image(imageSrc));
        
        this.price = price;
    }

    public String getMealName() {
        return mealName;
    }

    public ImageView getMealImage() {
        return mealImage;
    }

    public String getPrice() {
        return price;
    }
 
}
