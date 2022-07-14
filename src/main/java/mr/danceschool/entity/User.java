package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.*;
import mr.danceschool.utils.Gender;
import mr.danceschool.utils.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JoinColumn(name = "user_student_id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JoinColumn(name = "user_employee_id")
    private Employee employee;

    public User() {
    }

    public User(String login, String password, String firstname, String lastname, int discount, Gender gender) throws Exception {
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        Student.createStudent(this, discount, gender);
    }

    public User(String login, String password, String firstname, String lastname, int discount, String email, Gender gender) throws Exception {
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        Student.createStudent(this, discount, email, gender);
    }

    public User(String login, String password, String firstname, String lastname, List<String> phoneNumers, Date hiredDate, Role role) throws Exception{
        setLogin(login);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        Employee.createEmployee(this, phoneNumers, hiredDate, role);
        addRole(role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws Exception {
        if(login != null) {
            if(login.length() >= 3 && login.length() <= 30) {
                this.login = login;
            }else{
                throw new Exception("login size 3 30");
            }
        }else{
            throw new Exception("login NULL");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(password != null) {
            if(password.length() >= 3 && password.length() <= 300) {
                this.password = password;
            }else{
                throw new Exception("password size 3 300");
            }
        }else{
            throw new Exception("password NULL");
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) throws Exception {
        if(firstname != null) {
            if(firstname.length() >= 3 && firstname.length() <= 30) {
                this.firstname = firstname;
            }else{
                throw new Exception("firstname size 3 30");
            }
        }else{
            throw new Exception("firstname NULL");
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) throws Exception {
        if(lastname != null) {
            if(lastname.length() >= 3 && lastname.length() <= 30) {
                this.lastname = lastname;
            }else{
                throw new Exception("lastname size 3 30");
            }
        }else{
            throw new Exception("lastname NULL");
        }
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) throws Exception {
        if(!this.roles.contains(role)) {
            if(role != null) {
                this.roles.add(role);
            }else{
                throw new Exception("role null");
            }
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) throws Exception {
        if(this.student == null) {
            if (student != null) {
                this.student = student;
                addRole(Role.STUDENT);
                student.setUser(this);
            } else {
                throw new Exception("student NULL");
            }
        }else{
            throw new Exception("student NOT NULL");
        }
    }

    public void removeStudent() {
        this.student = null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) throws Exception {
        if(this.employee == null) {
            if (employee != null) {
                this.employee = employee;
                employee.setUser(this);
            } else {
                throw new Exception("employee NULL");
            }
        }else{
            throw new Exception("employee NOT NULL");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", roles=" + roles +
                '}';
    }
}
