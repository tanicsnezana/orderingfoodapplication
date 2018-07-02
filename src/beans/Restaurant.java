/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;

/**
 *
 * @author stan
 */
public class Restaurant {
    private String name;
    private String cuisine;
    private String description;
    private String visitingHours;
    private String address;
    private String phoneNumber;
    private List<Comment> comments;
    private List<Meal> meals;
    private String imageSrc;
    private double mark = 0;
    private boolean payingByCard;
    private boolean payingByCash;
    private boolean isGuest = false;
    private Orderer orderer = null;
    private int markNum = 0;

    public Orderer getOrderer() {
        return orderer;
    }

    public void setOrderer(Orderer orderer) {
        this.orderer = orderer;
    }
    
    public boolean isIsGuest() {
        return isGuest;
    }
    
    public int getMarkNum() {
        return markNum;
    }
    
    public void setMarkNum(int markNum) {
        this.markNum = markNum;
    }

    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }
    
    public boolean isPayingByCard() {
        return payingByCard;
    }

    public void setPayingByCard(boolean payingByCard) {
        this.payingByCard = payingByCard;
    }

    public boolean isPayingByCash() {
        return payingByCash;
    }

    public void setPayingByCash(boolean payingByCash) {
        this.payingByCash = payingByCash;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisitingHours() {
        return visitingHours;
    }

    public void setVisitingHours(String visitingHours) {
        this.visitingHours = visitingHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
