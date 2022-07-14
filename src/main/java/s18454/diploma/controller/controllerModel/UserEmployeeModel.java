package s18454.diploma.controller.controllerModel;

import s18454.diploma.utils.Role;

import java.util.Date;
import java.util.List;

public class UserEmployeeModel {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private List<String> phoneNumers;
    private Date hiredDate;
    private Role role;

    public UserEmployeeModel(String login, String password, String firstname, String lastname, List<String> phoneNumers, Date hiredDate, Role role) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumers = phoneNumers;
        this.hiredDate = hiredDate;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<String> getPhoneNumers() {
        return phoneNumers;
    }

    public void setPhoneNumers(List<String> phoneNumers) {
        this.phoneNumers = phoneNumers;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
