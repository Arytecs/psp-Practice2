/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.UI;

import static java.lang.System.err;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import store.LineOrder;

/**
 *
 * @author araceliteruel
 */
public class ShoppingCartScene {
    public static Scene getScene(Stage primaryStage){
        List<LineOrder> ordered=FXStore.store.getCurrentOrder().getLines();
        Label lbl=new Label();
        try{
            lbl.setText(ordered.get(0).getProduct().getName());
        } catch(Exception e){
            err.println("ERROR: "+e.getMessage());
        }
        BorderPane pane=new BorderPane();
        pane.setCenter(lbl);
        Scene scene=new Scene(pane,700,500);
        return scene;
    }
}
