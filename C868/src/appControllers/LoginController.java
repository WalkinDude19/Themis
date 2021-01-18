/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import appDatabase.DBConnection;
import appModels.User;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class LoginController {

    @FXML
    private TextField txt_username;
    @FXML
    private Label LoginUsernameLabel;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Label LoginPasswordLabel;
    @FXML
    private Button btn_login;
    @FXML
    private Label LoginLabel;
    @FXML
    private Button btn_exit;

    @FXML
    private void HandlerUserNameTxt(ActionEvent event) {
    }

    @FXML
    private void HandlerPasswordTxt(ActionEvent event) {
    }

    @FXML
    private void HandlerExitBtn(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Required");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            DBConnection.closeConnection();
            System.out.println("Program Exit.");
            System.exit(0);
        } else {
            System.out.println("Exit canceled.");
        }

    }

    @FXML
    private void HandlerLoginBtn(ActionEvent event) throws SQLException, IOException {
        String usernameInput = txt_username.getText();
        String passwordInput = txt_password.getText();
        Parent root;
        Stage stage;
        User user = new User();

        if (isValidPassword(usernameInput, passwordInput)) {

            user.setUsername(usernameInput);

            //calls mainscreen scene after successful login
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage = (Stage) btn_login.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Incorrect Username and/or Password");
            alert.setContentText("Enter valid Username and Password");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    //creates a new log file if one doesnt exist and inserts login information for current user
    private boolean isValidPassword(String username, String password) throws SQLException {

        //create statement object
        Statement statement = DBConnection.conn.createStatement();

        //write SQL statement
        String sqlStatement = "SELECT employee_password FROM c868users WHERE employee_login ='" + username + "'";

        //create resultset object
        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            if (result.getString("employee_password").equals(password)) {
                return true;
            }
        }
        return false;
    }

}
