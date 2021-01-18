/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appMain;

import appDatabase.DBConnection;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 *
 * @author mgtillot
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start...");
        Parent root = FXMLLoader.load(getClass().getResource("/appControllers/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        System.out.println("End of Start section");
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, Exception {

        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();

    }

}
