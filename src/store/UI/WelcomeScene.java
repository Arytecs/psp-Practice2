/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author araceliteruel
 */
public class WelcomeScene {
    
    
    public static Scene getScene(Stage primaryStage){
        //Welcome scene
        Label welcomeLbl=new Label("Welcome to FX Store");
        Label chooseLbl=new Label("Choose an option form the menu below");
        Button btn1=new Button("Buy products");
        Button btn2=new Button("Go to shopping cart");
        
        welcomeLbl.setStyle("-fx-font-size:20px");
        
        VBox pane=new VBox(20);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(welcomeLbl,chooseLbl,btn1,btn2);
        
        Scene scene=new Scene(pane,300,200);
        primaryStage.setScene(scene);
        btn1.setOnAction(e->{
            primaryStage.setScene(FXStore.scenes[FXStore.BUY_PRODUCTS_SCENE]);
        });
        btn2.setOnAction(e->{
            primaryStage.setScene(FXStore.scenes[FXStore.SHOPPING_CART_SCENE]);
        });
        return scene;
    }
}
