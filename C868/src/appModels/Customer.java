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
public class Customer extends Employee {

    private String customer_Department;

    public Customer(int empID, String empName, String empEmail, String empPhone, String empDept) {
        setEmpID(empID);
        setEmpName(empName);
        setEmpEmail(empEmail);
        setEmpPhone(empPhone);
        this.customer_Department = empDept;
    }

    public Customer(String name) {
        this.employee_name = name;
    }

    public Customer() {

    }

    public String getCustDept() {
        return customer_Department;
    }

    ;
    public void setCustDept(String customer_Department) {
        this.customer_Department = customer_Department;
    }

}
