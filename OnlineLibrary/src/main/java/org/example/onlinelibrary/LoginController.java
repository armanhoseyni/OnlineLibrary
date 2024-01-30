package org.example.onlinelibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

@FXML
        private TextField txt_username;

    @FXML
    private TextField txt_password;


    Connection con =DBcon.getCon();
    PreparedStatement st=null;
    ResultSet rs=null;
    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void Login(){
        try {
            st=con.prepareStatement("SELECT * FROM members WHERE username=? AND password=?");
            st.setString(1,txt_username.getText());
            st.setString(2,txt_password.getText());
            rs=st.executeQuery();
            if(rs.next()){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Login successFully", ButtonType.OK);
                alert.show();
                GoToHomePage();

            }
            else { Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Login Error", ButtonType.OK);
                alert.show();


            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }


    }

@FXML
private Button enterButton;

    @FXML
    private Stage currentStage;
    @FXML
    private void GoToHomePage() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteBook.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("2X2");
            newStage.setScene(new Scene(root, 800, 700));
            currentStage = (Stage) enterButton.getScene().getWindow();

            currentStage.close(); // Close the current stage

            newStage.show(); // Show the new stage

        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
