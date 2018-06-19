/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventhandlers;

import beans.Restaurant;
import javafx.event.EventHandler;


/**
 *
 * @author stan
 */
public abstract class EventHandlerRestaurant implements EventHandler{
    
    public Restaurant restaurant;
  
    public EventHandlerRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
