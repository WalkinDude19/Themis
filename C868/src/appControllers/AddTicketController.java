/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import appDatabase.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class AddTicketController implements Initializable {

    @FXML
    private Button btn_return;
    @FXML
    private Button btn_save;

    @FXML
    private DatePicker dp_date;
    @FXML
    private ComboBox cbo_customer;
    @FXML
    private ComboBox cbo_owner;
    @FXML
    private ComboBox cbo_priority;
    @FXML
    private ComboBox cbo_status;
    @FXML
    private TextField txt_title;
    @FXML
    private TextArea txt_desc;
    @FXML
    private TextArea txt_notes;
    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    private void HandlerAddTicketBtn(ActionEvent event) throws IOException, SQLException, Exception {

        try {
            if (is_empty() == true) {
                Alert add_alert = new Alert(Alert.AlertType.INFORMATION);
                add_alert.initModality(Modality.NONE);
                add_alert.setTitle("Add Ticket");
                add_alert.setHeaderText("Error");
                add_alert.setContentText("One or more fields is empty.");
                Optional<ButtonType> result = add_alert.showAndWait();
            } else {
                LocalDate local_date = dp_date.getValue();
                Date add_date = Date.from(local_date.atStartOfDay(defaultZoneId).toInstant());
                java.sql.Date sql_date = new java.sql.Date(add_date.getTime());
                String add_customer = cbo_customer.getValue().toString();
                String add_owner = cbo_owner.getValue().toString();
                String add_title = txt_title.getText();
                String add_desc = txt_desc.getText();
                String add_notes = txt_notes.getText();
                String add_status = cbo_status.getValue().toString();
                int add_priority = Integer.parseInt(cbo_priority.getValue().toString());
                int ticket_ID = -1;

                Query.add_Ticket(sql_date, add_customer, add_owner, add_title, add_desc, add_notes, add_status, add_priority);

                Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/MainTicket.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void HandlerReturnBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/MainTicket.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbo_customer.setItems(Query.get_Customers());
        cbo_owner.setItems(Query.get_Users());
        cbo_priority.setItems(Query.get_Priority());
        cbo_status.setItems(Query.get_Status());
        dp_date.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now()) > 0 );
            }
        });

    }

    public boolean is_empty() {
        if (cbo_customer.getItems() == null || cbo_owner.getItems() == null || cbo_priority.getItems() == null || cbo_status.getItems() == null || dp_date.getValue() == null || txt_desc.getText() == null || txt_notes.getText() == null || txt_title.getText() == null) {
            return true;
        } else {
            return false;
        }
    }

}
