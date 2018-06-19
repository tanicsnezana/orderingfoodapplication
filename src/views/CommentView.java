/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;


/**
 *
 * @author stan
 */
public class CommentView {
    protected String username;
    protected String comment;
    
    public CommentView() {
        
    }
    
    public CommentView(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
    
}
