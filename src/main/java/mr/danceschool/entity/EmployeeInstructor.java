package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Employee_instructor")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({ "groups" })
public class EmployeeInstructor extends Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToMany(mappedBy = "instructors")
    private Set<Group> groups = new HashSet<>();

    public EmployeeInstructor() {
        super();
    }

    public EmployeeInstructor(List<String> phoneNumers, Date hiredDate) throws Exception {
        super(phoneNumers, hiredDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws Exception {
        if(!this.groups.contains(group)) {
            if (group != null) {
                this.groups.add(group);
            } else {
                throw new Exception("group NULL");
            }
        }
    }

    public void removeGroup(Group group) {
        if(this.groups.contains(group)) {
            this.groups.remove(group);
        }
    }

    @Override
    public String toString() {
        return "EmployeeInstructor{"+
                "phoneNumers = "+this.getPhoneNumers() +
                ", hiredDate = "+this.getHiredDate() +
                ", firedDate = "+this.getFiredDate()+
                "}";
    }
}
