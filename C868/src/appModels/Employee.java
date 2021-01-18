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
public abstract class Employee {

    protected int employee_id;
    protected String employee_name;
    protected String employee_email;
    protected String employee_phone;

    public int getEmpID() {
        return employee_id;
    }

    ;
        public String getEmpName() {
        return employee_name;
    }

    ;
        public String getEmpEmail() {
        return employee_email;
    }

    ;
        public String getEmpPhone() {
        return employee_phone;
    }

    ;
        public void setEmpID(int i) {
        this.employee_id = i;
    }

    ;
        public void setEmpName(String string) {
        this.employee_name = string;
    }

    ;
        public void setEmpEmail(String string) {
        this.employee_email = string;
    }

    ;
        public void setEmpPhone(String string) {
        this.employee_phone = string;
    }
;

}
