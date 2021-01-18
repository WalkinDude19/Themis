/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import appDatabase.DBConnection;
import appModels.Tickets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class MainTicketController implements Initializable {

    @FXML
    private Button btn_return;
    @FXML
    private Button btn_addticket;
    @FXML
    private Button btn_editticket;
    @FXML
    private TextField txt_search;
    @FXML
    private TableView<Tickets> tbl_tickets;
    @FXML
    private TableColumn<Tickets, Integer> col_ticketid;
    @FXML
    private TableColumn<Tickets, Date> col_date;
    @FXML
    private TableColumn<Tickets, String> col_owned;
    @FXML
    private TableColumn<Tickets, Integer> col_priority;
    @FXML
    private TableColumn<Tickets, String> col_status;
    @FXML
    private TableColumn<Tickets, String> col_title;
    @FXML
    private TableColumn<Tickets, String> col_desc;
    @FXML
    private TableColumn<Tickets, String> col_notes;
    @FXML
    private TableColumn<Tickets, String> col_customer;

    ObservableList<Tickets> ticket_list = FXCollections.observableArrayList();

    @FXML
    private void HandlerAddTicketBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/AddTicket.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void HandlerEditTicketBtn(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/EditTicket.fxml"));
        Stage stage = (Stage) btn_editticket.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void HandlerSearchBtn(ActionEvent event) throws IOException {

        String search_text = txt_search.getText();
        //int search_id=Integer.parseInt(search_text);
        Connection connection;
        try {
            ticket_list.clear();
            connection = DBConnection.getConnection();
            ResultSet result_set = connection.createStatement().executeQuery("SELECT ticket_id, ticket_date, ticket_owner,ticket_priority,ticket_status,ticket_title\n"
                    + "FROM tickets WHERE  \"" + search_text + "\" IN (ticket_id,ticket_customer,ticket_owner)");
            while (result_set.next()) {

                ticket_list.add(new Tickets(result_set.getInt("ticket_id"),
                        result_set.getDate("ticket_date"),
                        result_set.getString("ticket_owner"),
                        result_set.getInt("ticket_priority"),
                        result_set.getString("ticket_status"),
                        result_set.getString("ticket_title")));

            }
            tbl_tickets.setItems(ticket_list);
if (ticket_list.isEmpty()){
Alert search_alert = new Alert(Alert.AlertType.INFORMATION);
            search_alert.initModality(Modality.NONE);
            search_alert.setTitle("Search Ticket");
            search_alert.setHeaderText("Error");
            search_alert.setContentText("No Tickets Found");
            Optional<ButtonType> result = search_alert.showAndWait();

}
else{}
        } catch (SQLException ex) {
            Logger.getLogger(MainTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PropertyValueFactory<Tickets, Integer> ticketIDFactory = new PropertyValueFactory<>("TicketID");
        PropertyValueFactory<Tickets, Date> ticketDateFactory = new PropertyValueFactory<>("IncidentDate");
        PropertyValueFactory<Tickets, String> ticketOwnedFactory = new PropertyValueFactory<>("TicketOwner");
        PropertyValueFactory<Tickets, Integer> ticketPriorityFactory = new PropertyValueFactory<>("TicketUrgency");
        PropertyValueFactory<Tickets, String> ticketStatusFactory = new PropertyValueFactory<>("TicketStatus");
        PropertyValueFactory<Tickets, String> ticketTitleFactory = new PropertyValueFactory<>("TicketTitle");
        col_ticketid.setCellValueFactory(ticketIDFactory);
        col_date.setCellValueFactory(ticketDateFactory);
        col_owned.setCellValueFactory(ticketOwnedFactory);
        col_priority.setCellValueFactory(ticketPriorityFactory);
        col_status.setCellValueFactory(ticketStatusFactory);
        col_title.setCellValueFactory(ticketTitleFactory);

    }

    @FXML
    private void HandlerReturnBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Connection connection;
        try {
            ticket_list.clear();
            connection = DBConnection.getConnection();
            ResultSet result_set = connection.createStatement().executeQuery("SELECT * FROM tickets ORDER BY ticket_id;");
            while (result_set.next()) {

                ticket_list.add(new Tickets(result_set.getInt("ticket_id"),
                        result_set.getDate("ticket_date"),
                        result_set.getString("ticket_customer"),
                        result_set.getString("ticket_owner"),
                        result_set.getString("ticket_title"),
                        result_set.getString("ticket_desc"),
                        result_set.getString("ticket_notes"),
                        result_set.getInt("ticket_priority"),
                        result_set.getString("ticket_status")
                ));

            }
            tbl_tickets.setItems(ticket_list);

        } catch (SQLException ex) {
            Logger.getLogger(MainTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PropertyValueFactory<Tickets, Integer> ticketIDFactory = new PropertyValueFactory<>("TicketID");
        PropertyValueFactory<Tickets, Date> ticketDateFactory = new PropertyValueFactory<>("IncidentDate");
        PropertyValueFactory<Tickets, String> ticketOwnedFactory = new PropertyValueFactory<>("TicketOwner");
        PropertyValueFactory<Tickets, Integer> ticketPriorityFactory = new PropertyValueFactory<>("TicketUrgency");
        PropertyValueFactory<Tickets, String> ticketStatusFactory = new PropertyValueFactory<>("TicketStatus");
        PropertyValueFactory<Tickets, String> ticketTitleFactory = new PropertyValueFactory<>("TicketTitle");
        PropertyValueFactory<Tickets, String> ticketDescFactory = new PropertyValueFactory<>("TicketDesc");
        PropertyValueFactory<Tickets, String> ticketNotesFactory = new PropertyValueFactory<>("TicketNotes");
        PropertyValueFactory<Tickets, String> ticketCustomerFactory = new PropertyValueFactory<>("TicketCustomer");
        col_ticketid.setCellValueFactory(ticketIDFactory);
        col_date.setCellValueFactory(ticketDateFactory);
        col_owned.setCellValueFactory(ticketOwnedFactory);
        col_priority.setCellValueFactory(ticketPriorityFactory);
        col_status.setCellValueFactory(ticketStatusFactory);
        col_title.setCellValueFactory(ticketTitleFactory);
        col_desc.setCellValueFactory(ticketDescFactory);
        col_notes.setCellValueFactory(ticketNotesFactory);
        col_customer.setCellValueFactory(ticketCustomerFactory);

    }
}
