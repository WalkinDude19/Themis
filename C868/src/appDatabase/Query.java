/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appDatabase;

import static appDatabase.DBConnection.conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 *
 * @author mgtillot
 */
public class Query {

    static ObservableList<String> users = FXCollections.observableArrayList();
    static ObservableList<String> employees = FXCollections.observableArrayList();
    static ObservableList<String> status = FXCollections.observableArrayList();
    static ObservableList<Integer> priority = FXCollections.observableArrayList();
    static ObservableList<String> reports = FXCollections.observableArrayList();

    public static ObservableList<String> get_Users() {

        try {
            users.removeAll(users); //prevents duplication
            ResultSet user_list = conn.createStatement().executeQuery("SELECT employee_name FROM c868users WHERE employee_name <> \"Test Account\";");
            while (user_list.next()) {
                users.add(user_list.getString("employee_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return users;
    }

    public static ObservableList<String> get_Customers() {

        try {
            employees.removeAll(employees); //prevents duplication
            ResultSet customer_list = conn.createStatement().executeQuery("SELECT employee_name FROM c868customers;");
            while (customer_list.next()) {
                employees.add(customer_list.getString("employee_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return employees;
    }

    public static ObservableList<String> get_Status() {
        status.removeAll(status); //stops list Duplication
        status.add("New");
        status.add("Assigned");
        status.add("In Progress");
        status.add("Resolved");
        status.add("Pending");
        return status;
    }

    public static ObservableList<Integer> get_Priority() {
        priority.removeAll(priority); //stops list Duplication
        priority.add(1);
        priority.add(2);
        priority.add(3);
        priority.add(4);
        priority.add(5);
        priority.add(6);
        priority.add(7);
        return priority;
    }

    public static ObservableList<String> get_Reports() {
        reports.removeAll(reports);
        reports.add("Tickets Older than 30 Days");
        reports.add("Prority 1 Tickets");
        reports.add("Open Tickets");
        return reports;
    }

    public static void add_Ticket(java.sql.Date date, String customer, String owner, String title, String desc, String notes, String status, int priority) throws Exception {
        try {
            int ticket_ID = -1;
            PreparedStatement ps = DBConnection.makeConnection().prepareStatement("Insert INTO tickets (ticket_date,ticket_customer,ticket_owner,ticket_title,ticket_desc,ticket_notes,ticket_status,ticket_priority) "
                    + " VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, date);
            ps.setString(2, customer);
            ps.setString(3, owner);
            ps.setString(4, title);
            ps.setString(5, desc);
            ps.setString(6, notes);
            ps.setString(7, status);
            ps.setInt(8, priority);

            int result = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ticket_ID = rs.getInt(1);
                System.out.println("Updated Ticket: " + ticket_ID);
            }

        } catch (SQLException e) {
            System.out.println("Error adding ticket");
        }

    }

    public static void edit_Ticket(int id, java.sql.Date date, String customer, String owner, String title, String desc, String notes, String status, int priority) throws Exception {
        try {
            int ticket_ID = -1;
            PreparedStatement ps = DBConnection.makeConnection().prepareStatement("UPDATE tickets"
                    + " SET ticket_date=?, ticket_customer=?, ticket_owner=?,ticket_title=?,ticket_desc=?,ticket_notes=?,ticket_status=?,ticket_priority=?"
                    + " WHERE ticket_id=?", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, date);
            ps.setString(2, customer);
            ps.setString(3, owner);
            ps.setString(4, title);
            ps.setString(5, desc);
            ps.setString(6, notes);
            ps.setString(7, status);
            ps.setInt(8, priority);
            ps.setInt(9, id);

            int i = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ticket_ID = rs.getInt(1);
                System.out.println("Updated Ticket: " + ticket_ID);
            }

            Alert del_alert = new Alert(Alert.AlertType.INFORMATION);
            del_alert.initModality(Modality.NONE);
            del_alert.setTitle("Update Ticket");
            del_alert.setHeaderText("Completed");
            del_alert.setContentText("Ticket " + id + " has been updated!");
            Optional<ButtonType> result = del_alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Error editing ticket");
        }
    }

    public static void delete_Ticket(int id) {
        try {
            conn.createStatement().executeUpdate(String.format("DELETE FROM tickets WHERE ticket_id='%s'", id));

        } catch (SQLException e) {
            System.out.println("Error deleting appointment");
        }
    }

    public static String old_ticketsQuery() throws SQLException {
        Connection connection = DBConnection.getConnection();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String str_date = formatter.format(date);

        try {

            StringBuilder report = new StringBuilder();
            report.append("-----Tickets older than 30 Days-----\n");
            report.append("-----" + str_date + "-----\n");

            ResultSet result_set = connection.createStatement().executeQuery(String.format("SELECT ticket_id,ticket_date,ticket_title FROM tickets WHERE ticket_date < now() - interval 30 DAY ORDER BY ticket_date ASC;"));
            while (result_set.next()) {
                report.append(result_set.getString("ticket_date")).append("     ").append(result_set.getString("ticket_id")).append("     ").append(result_set.getString("ticket_title")).append("\n");
            }

            return report.toString();

        } catch (SQLException e) {
            System.out.println("Error getting report");
            return "Report Failed";
        }

    }

    public static String high_priorityQuery() throws SQLException {
        Connection connection = DBConnection.getConnection();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String str_date = formatter.format(date);

        try {

            StringBuilder report = new StringBuilder();
            report.append("-----High Priority Tickets-----\n");
            report.append("-----" + str_date + "-----\n");

            ResultSet result_set = connection.createStatement().executeQuery(String.format("SELECT ticket_id,ticket_date,ticket_title FROM tickets WHERE ticket_priority=1 ORDER BY ticket_date ASC;"));
            while (result_set.next()) {
                report.append(result_set.getString("ticket_id")).append("     ").append(result_set.getString("ticket_date")).append("     ").append(result_set.getString("ticket_title")).append("\n");
            }

            return report.toString();

        } catch (SQLException e) {
            System.out.println("Error getting report");
            return "Report Failed";
        }

    }

    public static String open_Query() throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String str_date = formatter.format(date);

        Connection connection = DBConnection.getConnection();
        try {

            StringBuilder report = new StringBuilder();
            report.append("-----Unowned Tickets-----\n");
            report.append("-----" + str_date + "-----\n");

            ResultSet result_set = connection.createStatement().executeQuery(String.format("select ticket_id,ticket_date,ticket_title from tickets where ticket_status IN (\"New\",\"Assigned\",\"In Progress\",\"Pending\") ORDER BY ticket_id ASC"));
            while (result_set.next()) {
                report.append(result_set.getString("ticket_id")).append("     ").append(result_set.getString("ticket_date")).append("     ").append(result_set.getString("ticket_title")).append("\n");
            }

            return report.toString();

        } catch (SQLException e) {
            System.out.println("Error getting report");
            return "Report Failed";
        }

    }

}
