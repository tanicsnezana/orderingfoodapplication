/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import beans.Orderer;

/**
 *
 * @author stan
 */
public class CommentsViewOrderer extends CommentView {
    private Orderer orderer;
    
    public CommentsViewOrderer(String username, String comment, Orderer orderer) {
        this.username = username;
        this.comment = comment;
        this.orderer = orderer;
    }
    
    public Orderer getOrderer() {
        return orderer;
    }
    
}
