/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appControllers;

import appDatabase.DBConnection;
import appModels.Tickets;
import appDatabase.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author mgtillot
 */
public class EditTicketController implements Initializable {

    @FXML
    private Button btn_return;
    @FXML
    private Button btn_save;
    @FXML
    private Label lbl_ticketid;
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
    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    private void HandlerReturnBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/MainTicket.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void HandlerSearchBtn(ActionEvent event) {
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
    private void HandlerEditBtn(ActionEvent event) {
   
            
       if(tbl_tickets.getSelectionModel().getSelectedItem()!= null){ 
           Tickets edit_ticket = tbl_tickets.getSelectionModel().getSelectedItem();
        lbl_ticketid.setText(Integer.toString(edit_ticket.getTicketID()));
        Date edit_date = edit_ticket.getIncidentDate();
        java.sql.Date date = new java.sql.Date(edit_date.getTime());
        LocalDate l_date = date.toLocalDate();
        dp_date.setValue(l_date);
        cbo_customer.setValue(edit_ticket.getTicketCustomer());
        cbo_owner.setValue(edit_ticket.getTicketOwner());
        cbo_priority.setValue(edit_ticket.getTicketUrgency());
        cbo_status.setValue(edit_ticket.getTicketStatus());
        txt_title.setText(edit_ticket.getTicketTitle());
        txt_desc.setText(edit_ticket.getTicketDesc());
        txt_notes.setText(edit_ticket.getTicketNotes());
        editablefields(false);
       }
       else{
                   Alert edit_alert = new Alert(Alert.AlertType.INFORMATION);
            edit_alert.initModality(Modality.NONE);
            edit_alert.setTitle("No Ticket");
            edit_alert.setHeaderText("Error");
            edit_alert.setContentText("No Ticket Selected for Editing");
             Optional<ButtonType> result = edit_alert.showAndWait();
       }
        
        

    }

    @FXML
    private void HandlerSaveBtn(ActionEvent event) throws IOException, Exception {

        try {

            int edit_id = Integer.parseInt(lbl_ticketid.getText());

            LocalDate local_date = dp_date.getValue();
            Date edit_date = Date.from(local_date.atStartOfDay(defaultZoneId).toInstant());
            java.sql.Date sql_date = new java.sql.Date(edit_date.getTime());
            String edit_customer = cbo_customer.getValue().toString();
            String edit_owner = cbo_owner.getValue().toString();
            String edit_title = txt_title.getText();
            String edit_desc = txt_desc.getText();
            String edit_notes = txt_notes.getText();
            String edit_status = cbo_status.getValue().toString();
            int edit_priority = Integer.parseInt(cbo_priority.getValue().toString());
            int ticket_ID = -1;

            Query.edit_Ticket(edit_id, sql_date, edit_customer, edit_owner, edit_title, edit_desc, edit_notes, edit_status, edit_priority);

            Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/EditTicket.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    private void HandlerDeleteBtn(ActionEvent event) {
        
        
        
        try {

            if(tbl_tickets.getSelectionModel().getSelectedItem() != null){
            Tickets del_ticket = tbl_tickets.getSelectionModel().getSelectedItem();
            String str_ID = Integer.toString(del_ticket.getTicketID());
            Alert del_alert = new Alert(Alert.AlertType.CONFIRMATION);
            del_alert.initModality(Modality.NONE);
            del_alert.setTitle("Delete Ticket?");
            del_alert.setHeaderText("Confirm");
            del_alert.setContentText("Confirm deletion of ticket: " + str_ID + ".");
            Optional<ButtonType> result = del_alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Query.delete_Ticket(del_ticket.getTicketID());

                Parent parent = FXMLLoader.load(getClass().getResource("/appControllers/EditTicket.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
            }
            }
            else{
                        Alert del_alert = new Alert(Alert.AlertType.INFORMATION);
            del_alert.initModality(Modality.NONE);
            del_alert.setTitle("Delete Ticket");
            del_alert.setHeaderText("Error");
            del_alert.setContentText("No Ticket Selected");
            Optional<ButtonType> result = del_alert.showAndWait();
            
            }
        } catch (IOException e) {

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbo_customer.setItems(Query.get_Customers());
        cbo_owner.setItems(Query.get_Users());
        cbo_priority.setItems(Query.get_Priority());
        cbo_status.setItems(Query.get_Status());

        //populate table
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
        lbl_ticketid.setText("###");
        dp_date.setValue(null);
        txt_title.clear();
        txt_desc.clear();
        txt_notes.clear();
        editablefields(true);
        dp_date.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now()) > 0 );
            }
        });

    }

    public void editablefields(boolean bool) {
        dp_date.setDisable(bool);
        cbo_customer.setDisable(bool);
        cbo_owner.setDisable(bool);
        cbo_priority.setDisable(bool);
        cbo_status.setDisable(bool);
        txt_title.setDisable(bool);
        txt_desc.setDisable(bool);
        txt_notes.setDisable(bool);
    }

}
