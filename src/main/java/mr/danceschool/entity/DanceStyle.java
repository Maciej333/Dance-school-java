package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Dance_style")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
@JsonIgnoreProperties({ "studentDanceStyleList", "groups" })
public class DanceStyle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String countryOfOrigin;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy="danceStyle", cascade = CascadeType.ALL)
    private Set<StudentDanceStyle> studentDanceStyleList = new HashSet<>();

    @OneToMany(mappedBy="danceStyle", cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

    public DanceStyle() {
    }

    public DanceStyle(String name, String countryOfOrigin, String description) throws Exception {
        setName(name);
        setCountryOfOrigin(countryOfOrigin);
        setDescription(description);
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
            if(name.length() >= 3 && name.length() <= 30) {
                this.name = name;
            }else{
                throw new Exception("name size 3 30");
            }
        }else {
            throw new Exception("name NULL");
        }
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) throws Exception {
        if(countryOfOrigin != null){
            if(countryOfOrigin.length() >= 3 && countryOfOrigin.length() <= 30) {
                this.countryOfOrigin = countryOfOrigin;
            }else{
                throw new Exception("countryOfOrigin size 3 30");
            }
        }else {
            throw new Exception("countryOfOrigin NULL");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if(description != null){
            if(description.length() >= 3) {
                this.description = description;
            }else{
                throw new Exception("description < 3");
            }
        }else {
            throw new Exception("description NULL");
        }
    }

    public Set<StudentDanceStyle> getStudentDanceStyleList() {
        return studentDanceStyleList;
    }

    public void addStudentDanceStyle(StudentDanceStyle sds) throws Exception{
        if(!this.studentDanceStyleList.contains(sds)) {
            if (sds != null) {
                this.studentDanceStyleList.add(sds);
                sds.setDanceStyle(this);
            } else {
                throw new Exception("StudentDanceStyle NULL");
            }
        }
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws Exception {
        if(!this.groups.contains(group)) {
            if (group != null) {
                this.groups.add(group);
                group.setDanceStyle(this);
            } else {
                throw new Exception("group NULL");
            }
        }
    }

    @Override
    public String toString() {
        return "DanceStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
