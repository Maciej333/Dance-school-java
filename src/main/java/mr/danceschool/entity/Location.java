package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Location")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "adress")
@JsonIgnoreProperties({ "groups" })
public class Location {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "adress", nullable = false)
    private String adress;

    @OneToMany(mappedBy="location", cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();


    public Location() {
    }

    public Location(String adress) throws Exception {
        setAdress(adress);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) throws Exception {
        if(adress != null) {
            this.adress = adress;
        }else{
            throw new Exception("adress NULL");
        }
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws Exception {
        if(!this.groups.contains(group)) {
            if (group != null) {
                this.groups.add(group);
                group.setLocation(this);
            } else {
                throw new Exception("group NULL");
            }
        }
    }

    public void removeGroup(Group group) throws Exception{
        if(this.groups.contains(group)){
            this.groups.remove(group);
        } else {
            throw new Exception("group not in set");
        }
    }
}
