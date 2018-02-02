package controllers;

import database.UserDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

import static values.strings.*;


public class LoginController {
    @FXML
    TextField textUsername;
    @FXML
    PasswordField textPassword;
    private UserDB users = new UserDB();

    /**
     This is button handle sign in button. if it is clicked, it will check to see weather the user existed or not if not
     it will not show other form
     */
    public void onMouseClickSignIn() throws ClassNotFoundException, SQLException, IOException {
        String userName = textUsername.getText();
        String password = textPassword.getText();
        //check users exists in MysqlDatabase
        if (users.checkUserExists(userName,password)) {
            JOptionPane.showMessageDialog(null,messageLoginSuccessful);
            clearTextFields();
            showManagementStage();
        }else{
            JOptionPane.showMessageDialog(null,messageFailledLogin);
        }
    }

    /**
     * This method used to remove TextField username and password
     */
    private void clearTextFields() {
        textUsername.clear();
        textPassword.clear();
    }

    /**
     * This method used for open a new Stage which is ManagementForm.fxml
     */
    private void showManagementStage() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ManagementForm.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(formSignInTitle);
            stage.setScene(new Scene(root1));
            stage.show();
    }
}
