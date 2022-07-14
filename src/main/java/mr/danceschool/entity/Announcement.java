package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Announcement")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Announcement {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    public Announcement() {
    }

    public Announcement(String text) throws Exception {
        setDate(new Date());
        setText(text);
        setGroup(null);
    }

    public Announcement(String text, Group group) throws Exception {
        setDate(new Date());
        setText(text);
        setGroup(group);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws Exception {
        if(date != null) {
            this.date = date;
        }else{
            throw new Exception("date NULL");
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) throws Exception {
        if(text != null) {
            this.text = text;
        }else{
            throw new Exception("text NULL");
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) throws Exception {
        if(this.group == null) {
            if (group != null) {
                this.group = group;
                group.addAnnouncement(this);
            } else {
                this.group = null;
            }
        }
    }
}
