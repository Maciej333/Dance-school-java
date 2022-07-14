package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Employee_director")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class EmployeeDirector extends Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    public EmployeeDirector() {
        super();
    }

    public EmployeeDirector(List<String> phoneNumers, Date hiredDate) throws Exception {
        super(phoneNumers, hiredDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmployeeDirector{"+
                "phoneNumers = "+this.getPhoneNumers() +
                ", hiredDate = "+this.getHiredDate() +
                ", firedDate = "+this.getFiredDate()+
                "}";
    }
}
