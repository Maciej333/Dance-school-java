package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mr.danceschool.utils.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public abstract class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ElementCollection
    private List<String> phoneNumers;

    @Column(name = "hired_date", nullable = false)
    private Date hiredDate;

    @Column(name = "fired_date", nullable = true)
    private Date firedDate;

    @OneToOne
    private User user;

    public Employee() {
    }
    public Employee(List<String> phoneNumers, Date hiredDate) throws Exception {
        setPhoneNumers(phoneNumers);
        setHiredDate(hiredDate);
        setFiredDate(null);
    }

    public static Employee createEmployee(User user, List<String> phoneNumers, Date hiredDate, Role role) throws Exception {
        if(user == null || role == null){
            throw new Exception("createEmployee - user/role null");
        }else{
            if(role == Role.DIRECTOR){
                Employee employee = new EmployeeDirector(phoneNumers, hiredDate);
                employee.setUser(user);
                return employee;
            }else if(role == Role.INSTRUCTOR){
                Employee employee = new EmployeeInstructor(phoneNumers, hiredDate);
                employee.setUser(user);
                return employee;
            } else {
                throw new Exception("createEmployee - role");
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getPhoneNumers() {
        return phoneNumers;
    }

    public void setPhoneNumers(List<String> phoneNumers) throws Exception {
        if(phoneNumers != null) {
            if (phoneNumers.size() > 0) {
                this.phoneNumers = phoneNumers;
            } else {
                throw new Exception("phoneNumers min size = 1");
            }
        }else{
            throw new Exception("phoneNumers NULL");
        }
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) throws Exception {
        if(hiredDate != null) {
            this.hiredDate = hiredDate;
        }else{
            throw new Exception("hiredDate NULL");
        }
    }

    public Date getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(Date firedDate) {
        this.firedDate = firedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws Exception {
        if(this.user == null) {
            if (user != null) {
                this.user = user;
                user.setEmployee(this);
            } else {
                throw new Exception("user null");
            }
        }
    }
}
