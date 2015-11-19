/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.UI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import store.Product;

import store.Store;

/**
 *
 * @author araceliteruel
 */
public class BuyProductsScene {
    
    public static Scene getScene(Stage primaryStage){
        //Buy products scene
        Set<String> items=FXStore.store.getCategories();
        ComboBox categories=new ComboBox();
        categories.getItems().addAll(items);

        Label choosenProduct=new Label();
        
        TableView table=new TableView();
        TableColumn colRef=new TableColumn("Ref");
        TableColumn colName=new TableColumn("Name");
        TableColumn colPrice=new TableColumn("Price(€)");
        
        colRef.setPrefWidth(70);
        colName.setPrefWidth(550);
        colPrice.setPrefWidth(70);
        
        table.getColumns().addAll(colRef,colName,colPrice);
        
        
        
        Button btnReturn=new Button("<< Main Menu");
        Button btnAdd=new Button("Add to cart >>");
        
        TextField quantity=new TextField("0");
        btnAdd.setDisable(true);
        quantity.setDisable(true);
        
        HBox hbox=new HBox(100);
        
        HBox hbox1=new HBox(3);
        HBox hbox2=new HBox(3);
        
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        
        hbox1.getChildren().addAll(btnReturn,choosenProduct);
        hbox2.getChildren().addAll(quantity,btnAdd);
        
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(hbox1,hbox2);
        
        BorderPane pane=new BorderPane();
        pane.setTop(categories);
        pane.setCenter(table);
        pane.setBottom(hbox);
        
        categories.setOnAction(e->{
            Set<Product> productsList=new HashSet();
            productsList.addAll(FXStore.store.getProductsByCategory(categories.getValue().toString()));

            ObservableList<Product> products=FXCollections.observableArrayList(productsList);
            
            colRef.setCellValueFactory(new PropertyValueFactory("reference"));
            colName.setCellValueFactory(new PropertyValueFactory("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory("price"));
            table.setItems(products); 
                        
            btnAdd.setDisable(true);
            quantity.setDisable(true);
            
            
        });
        
        table.getSelectionModel().selectedIndexProperty().addListener(ex->{
            btnAdd.setDisable(false);
            quantity.setDisable(false);
                           
        });
        
        table.setOnMouseClicked(ex->{
                if(table.getSelectionModel().getSelectedIndex()>0) {
                    Product choosen = (Product)table.getSelectionModel().getSelectedItem();
                    choosenProduct.setText(choosen.getName());
                }
                else{
                    btnAdd.setDisable(true);
                    quantity.setDisable(true);
                }
            });
            
            
        
        
        
        btnReturn.setOnAction(e->{
            primaryStage.setScene(FXStore.scenes[FXStore.WELCOME_SCENE]);
        });
        
        btnAdd.setOnAction(e->{
            
            Product chosen=(Product)table.getSelectionModel().getSelectedItem();
            
            FXStore.store.getCurrentOrder().addProduct(chosen, Integer.parseInt(quantity.getText()));
            primaryStage.setScene(FXStore.scenes[FXStore.SHOPPING_CART_SCENE]);
        });
        
        Scene scene=new Scene(pane,700,400);
        
        return scene;
    }
}
