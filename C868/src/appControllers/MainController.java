/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import appDatabase.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class MainController {

    @FXML
    private Button btn_exit;
    @FXML
    private Button btn_tickets;
    @FXML
    private Button btn_reports;

    @FXML
    private void HandlerExitBtn(ActionEvent event) throws IOException, SQLException {

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
    private void HandlerTicketsBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/MainTicket.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void HandlerReportsBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
