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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author araceliteruel
 */
public class LoginScene {
    
    
    public static Scene getScene(Stage primaryStage){
        
        //Login scene
        VBox pane=new VBox(30);
        HBox hrb=new HBox(50);
        HBox hl1=new HBox(50);
        VBox verti1=new VBox(20);
        VBox verti2=new VBox(20);
        
        ToggleGroup tg=new ToggleGroup();
        RadioButton rb1=new RadioButton("Sign in");
        RadioButton rb2=new RadioButton("Sing up");
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb2.setSelected(true);
        
        Label loginLbl=new Label("Login:");
        TextField loginTf=new TextField();
        
        Label passLbl=new Label("Password:");
        PasswordField pass1Tf=new PasswordField();
        
        Label pass2Lbl=new Label("Repeat password:");
        PasswordField pass2Tf=new PasswordField();
        
        Button btn=new Button("Go");
        
        Label errorLbl=new Label("");
        errorLbl.setTextFill(Color.RED);
        
        hrb.setAlignment(Pos.CENTER);
        hrb.getChildren().addAll(rb1,rb2);
        
        verti1.setAlignment(Pos.CENTER_LEFT);
        verti1.getChildren().addAll(loginLbl,passLbl,pass2Lbl);
        
        verti2.setAlignment(Pos.CENTER);
        verti2.getChildren().addAll(loginTf,pass1Tf,pass2Tf);
        
        hl1.setAlignment(Pos.CENTER);
        hl1.getChildren().addAll(verti1,verti2);
        
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(hrb,hl1,btn,errorLbl);
        
        //Event RadioButton
        rb1.setOnAction(e->{
           if(rb1.isSelected()){
            pass2Lbl.setVisible(false);
            pass2Tf.setVisible(false);
           }
        });
        
        rb2.setOnAction(e->{
            if(!pass2Lbl.visibleProperty().getValue()){
                pass2Lbl.setVisible(true);
                pass2Tf.setVisible(true);
            }
        });
        
        btn.setOnAction(e->{
            if(rb1.isSelected()){
                if(!comprobarUser(loginTf,pass1Tf)){
                    errorLbl.setText("ERROR: Invalid user login or password");
                }
                else{
                    primaryStage.setScene(FXStore.scenes[FXStore.WELCOME_SCENE]);
                }
            }else{
                if(pass1Tf.getText().equals(pass2Tf.getText())){
                    if(existUser(loginTf)){
                        errorLbl.setText("ERROR: User is already signed up");
                    }
                    else if(!comprobarReg(loginTf,pass1Tf)){
                        errorLbl.setText("Unknown error ocurred");
                    }else{
                        primaryStage.setScene(FXStore.scenes[FXStore.WELCOME_SCENE]);
                    }
                }else{
                    errorLbl.setText("ERROR: The two password must be equals");
                }
            }
        });
        
        
        Scene scene=new Scene(pane,400,400);
        primaryStage.setTitle("Login");
        return scene;
        
        
    }

    private static boolean comprobarUser(TextField loginTf, PasswordField passTf) {
        boolean login=FXStore.store.login(loginTf.getText(),passTf.getText());
        return login;
    }

    private static boolean comprobarReg(TextField loginTf, PasswordField pass1Tf) {
        boolean register=FXStore.store.regUser(loginTf.getText(), pass1Tf.getText());
        return register;
    }

    private static boolean existUser(TextField loginTf) {
        boolean register=FXStore.store.userExists(loginTf.getText());
        return register;
    }
}
