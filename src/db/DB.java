/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.Category;
import beans.Comment;
import beans.Meal;
import beans.Orderer;
import beans.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stan
 */
public class DB {
   public static List<Orderer> users = new ArrayList<Orderer>();
    public static List<Restaurant> restaurants = new ArrayList<Restaurant>();
    
    static{
        Orderer orderer1 = new Orderer();
        orderer1.setAddress("Borska 36a");
        orderer1.setName("Snezana");
        orderer1.setSurname("Tanic");
        orderer1.setPassword("sifra123");
        orderer1.setPhoneNumber("+381655777396");
        orderer1.setUsername("s_tanic");
        users.add(orderer1);
        
        Comment restauran1comment1 = new Comment();
        restauran1comment1.setOrderer(orderer1);
        restauran1comment1.setComment("Odlican restoran! Sve preporuke!");
        
        List<Comment> restaurant1Comments = new ArrayList<Comment>();
        restaurant1Comments.add(restauran1comment1);
        
        /* Creating meals */
        
        List<Meal> meals = new ArrayList<Meal>();
        
        /* Creating meal 1 */
        Meal meal1 = new Meal();
        meal1.setCategory(Category.LUNCH);
        meal1.setKind("mexican");
        meal1.setName("Burito Achiote Chicken");
        List<String> ingredients = new ArrayList<String>();
        ingredients.add("Marinated drumstick");
        ingredients.add("Pepper");
        ingredients.add("Spices");
        meal1.setIngredients(ingredients);
        meal1.setDescription("Delicious healthy burito with marinated drumstick and spices");
        meal1.setPrice(370);
        meal1.setImageSrc("meals/meal1.jpg");
        
        List<Comment> meal1Comments = new ArrayList<Comment>();
        Comment meal1comment1 = new Comment();
        meal1comment1.setOrderer(orderer1);
        meal1comment1.setComment("Ukusno i velika porcija");
        meal1Comments.add(meal1comment1);
        meal1.setComments(meal1Comments);
        
        
        meals.add(meal1);
        
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setAddress("Karadjordjeva 65");
        restaurant1.setDescription("Something");
        restaurant1.setCuisine("mexican");
        restaurant1.setName("Burrito Madre");
        restaurant1.setPhoneNumber("011345678");
        restaurant1.setVisitingHours("9:00 - 23:30");
        restaurant1.setImageSrc("restaurants/img1.jpg");
        restaurant1.setPayingByCard(true);
        restaurant1.setPayingByCash(true);
        restaurant1.setComments(restaurant1Comments);
        restaurant1.setMeals(meals);
        restaurants.add(restaurant1);
        
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setAddress("Kosovska 18");
        restaurant2.setDescription("Something");
        restaurant2.setCuisine("Italian, Mediteran");
        restaurant2.setName("Garden food");
        restaurant2.setPhoneNumber("+381645323457");
        restaurant2.setVisitingHours("6:00 - 23:00");
        restaurant2.setImageSrc("restaurants/img2.jpg");
        restaurant2.setPayingByCard(false);
        restaurant2.setPayingByCash(true);
        restaurants.add(restaurant2);
    } 
}
