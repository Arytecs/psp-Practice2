/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.UI;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import store.Product;
import store.Store;

/**
 *
 * @author araceliteruel
 */
public class FXStore extends Application{
    public static final int MAX_SCENES = 4;
    public static final int LOGIN_SCENE = 0;
    public static final int WELCOME_SCENE = 1; 
    public static final int BUY_PRODUCTS_SCENE = 2; 
    public static final int SHOPPING_CART_SCENE = 3;
    
    static Scene[] scenes = new Scene[MAX_SCENES];
    static Store store=new Store("directory","users.txt","products.txt");
    
    LoginScene log;
    
    @Override
    public void start(Stage primaryStage) {
        scenes[LOGIN_SCENE]=LoginScene.getScene(primaryStage);
        scenes[WELCOME_SCENE]=WelcomeScene.getScene(primaryStage);
        scenes[BUY_PRODUCTS_SCENE]=BuyProductsScene.getScene(primaryStage);
        scenes[SHOPPING_CART_SCENE]=ShoppingCartScene.getScene(primaryStage);
        primaryStage.setScene(scenes[LOGIN_SCENE]);
        primaryStage.setTitle("Store Management");
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
