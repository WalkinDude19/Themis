/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appModels;

/**
 *
 * @author mgtillot
 */
public class User extends Employee {

    private static String username;
    private static String password;

    public User() {
        username = null;
        password = null;

    }

    public User(int empID, String empName, String empEmail, String empPhone, String username, String password) {
        setEmpID(empID);
        setEmpName(empName);
        setEmpEmail(empEmail);
        setEmpPhone(empPhone);
        User.username = username;
        User.password = password;
    }

    public User(String user_name) {
        this.employee_name = user_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    public void setPassword(String password) {
        User.password = password;
    }

}
