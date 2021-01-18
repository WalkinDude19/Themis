/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import static appDatabase.Query.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class ReportsController implements Initializable {

    @FXML
    private Button btn_return;
    @FXML
    private ComboBox cbo_reports;
    @FXML
    private Button btn_reset;
    @FXML
    private TextArea txt_reports;
    @FXML
    private Button btn_reports;

    @FXML
    private void HandlerReturnBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void HandlerResetBtn(ActionEvent event) {

        txt_reports.clear();
        btn_reset.setDisable(true);
        cbo_reports.setDisable(false);
        cbo_reports.setItems(get_Reports());
        btn_reports.setDisable(false);
        txt_reports.setDisable(true);
    }

    @FXML
    private void HandlerReportBtn(ActionEvent event) throws SQLException {
        txt_reports.clear();
        txt_reports.setDisable(false);
        cbo_reports.setDisable(true);
        btn_reports.setDisable(true);
        btn_reset.setDisable(false);

        String chosen_Report = cbo_reports.getValue().toString();

        switch (chosen_Report) {
            case "Tickets Older than 30 Days":
                rpt_Old_Tickets();
                break;
            case "Prority 1 Tickets":
                rpt_High_Priority();
                break;
            case "Open Tickets":
                rpt_Open();
                break;
            default:
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbo_reports.setItems(get_Reports());
        txt_reports.setDisable(true);
    }

    private void rpt_Old_Tickets() throws SQLException {
   txt_reports.setText(old_ticketsQuery());
    }

    private void rpt_High_Priority() throws SQLException {
        txt_reports.setText(high_priorityQuery());
    }

    private void rpt_Open() throws SQLException {
       txt_reports.setText(open_Query());
    }

}
