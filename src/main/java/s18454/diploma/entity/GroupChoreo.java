package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import s18454.diploma.utils.DanceLevel;
import s18454.diploma.utils.Gender;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Group_choreo")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class GroupChoreo extends Group{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    public GroupChoreo() {
    }

    public GroupChoreo(Date createDate, List<Gender> genderList, DanceLevel danceLevel, DanceStyle danceStyle, Location location, Set<EmployeeInstructor> instructors, String name) throws Exception {
        super(createDate, genderList, danceLevel, danceStyle, location, instructors);
        setName(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name != null){
            if(name.length() >= 3 && name.length() <= 30){
                this.name = name;
            }else{
                throw new Exception("name size 3 to 30");
            }
        }else{
            throw new Exception("name NULL");
        }
    }

    @Override
    public String toString() {
        return "GroupChoreo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
