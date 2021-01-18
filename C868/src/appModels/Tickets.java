/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appModels;

import java.util.Date;

/**
 *
 * @author mgtillot
 */
public class Tickets {

    private int ticket_ID;
    private Date incident_date;
    private String ticket_Customer;
    private String ticket_Owner;
    private String ticket_Title;
    private String ticket_Description;
    private String ticket_Notes;
    private int ticket_Urgency;
    private String ticket_Status;

    public Tickets(int ticketid, Date date, String customer, String owner, String title, String description, String notes, int urgency, String status) {
        this.ticket_ID = ticketid;
        this.incident_date = date;
        this.ticket_Customer = customer;
        this.ticket_Owner = owner;
        this.ticket_Title = title;
        this.ticket_Description = description;
        this.ticket_Notes = notes;
        this.ticket_Urgency = urgency;
        this.ticket_Status = status;
    }

    public Tickets(int ticketid, Date date, String owner, int priority, String status, String title) {
        this.ticket_ID = ticketid;
        this.incident_date = date;
        this.ticket_Owner = owner;
        this.ticket_Title = title;
        this.ticket_Status = status;
        this.ticket_Urgency = priority;

    }



    public int getTicketID() {
        return ticket_ID;
    }

    public Date getIncidentDate() {
        return incident_date;
    }

    public String getTicketCustomer() {
        return ticket_Customer;
    }

    public String getTicketOwner() {
        return ticket_Owner;
    }

    public String getTicketTitle() {
        return ticket_Title;
    }

    public String getTicketDesc() {
        return ticket_Description;
    }

    public String getTicketNotes() {
        return ticket_Notes;
    }

    public int getTicketUrgency() {
        return ticket_Urgency;
    }

    public String getTicketStatus() {
        return ticket_Status;
    }

    public void setTicketID(int i) {
        this.ticket_ID = i;
    }

    public void setIncidentDate(Date date) {
        this.incident_date = date;
    }

    public void setTicketCustomer(String name) {
        this.ticket_Customer = name;
    }

    public void setTicketOwner(String name) {
        this.ticket_Owner = name;
    }

    public void setTicketTitle(String title) {
        this.ticket_Title = title;
    }

    public void setTicketDesc(String desc) {
        this.ticket_Description = desc;
    }

    public void setTicketNotes(String notes) {
        this.ticket_Notes = notes;
    }

    public void setTicketUrgency(int i) {
        this.ticket_Urgency = i;
    }

    public void setTicketStatus(String status) {
        this.ticket_Status = status;
    }

}
