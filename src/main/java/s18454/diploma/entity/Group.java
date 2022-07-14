package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.*;
import s18454.diploma.utils.DanceLevel;
import s18454.diploma.utils.Gender;
import s18454.diploma.utils.GroupStatus;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Group")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({ "students", "announcements" })
public abstract class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "group_status", nullable = false)
    private GroupStatus groupStatus;

    @ElementCollection
    private List<Gender> genderList;

    @Column(name = "dance_level", nullable = false)
    private DanceLevel danceLevel;

    @ManyToOne
    @JoinColumn(name="dance_style_id")
    private DanceStyle danceStyle;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "group_instructors",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private Set<EmployeeInstructor> instructors;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL)
    private Set<Announcement> announcements = new HashSet<>();

    public Group() {
    }

    public Group(Date createDate, List<Gender> genderList, DanceLevel danceLevel, DanceStyle danceStyle, Location location, Set<EmployeeInstructor> instructors) throws Exception {
        setCreateDate(createDate);
        setGroupStatus(GroupStatus.OPEN);
        setGenderList(genderList);
        setDanceLevel(danceLevel);
        setDanceStyle(danceStyle);
        setLocation(location);
        setInstructors(instructors);
    }

    public static Group createGroup(Date createDate, List<Gender> genderList, DanceLevel danceLevel, DanceStyle danceStyle, Location location, Set<EmployeeInstructor> instructors, DayOfWeek classroomDay, LocalTime classroomStartTime, int classroomDuration) throws Exception{
        Group group = new GroupCourse(createDate, genderList, danceLevel, danceStyle, location, instructors, classroomDay, classroomStartTime, classroomDuration);
        return group;
    }

    public static Group createGroup(Date createDate, List<Gender> genderList, DanceLevel danceLevel, DanceStyle danceStyle, Location location, Set<EmployeeInstructor> instructors, String name) throws Exception{
        Group group = new GroupChoreo(createDate, genderList, danceLevel, danceStyle, location, instructors, name);
        return group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) throws Exception {
        if(createDate != null) {
            this.createDate = createDate;
        }else{
            throw new Exception("createDate NULL");
        }
    }

    public GroupStatus getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(GroupStatus groupStatus) throws Exception {
        if(groupStatus != null) {
            this.groupStatus = groupStatus;
        }else{
            throw new Exception("groupStatus NULL");
        }
    }

    public List<Gender> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<Gender> genderList) throws Exception {
        if(genderList != null) {
            if(genderList.size() == 1 || genderList.size() == 2) {
                this.genderList = genderList;
            }else{
                throw new Exception("genderList size 1 to 2");
            }
        }else{
            throw new Exception("genderList NULL");
        }
    }

    public DanceLevel getDanceLevel() {
        return danceLevel;
    }

    public void setDanceLevel(DanceLevel danceLevel) throws Exception {
        if(danceLevel != null) {
            this.danceLevel = danceLevel;
        }else{
            throw new Exception("danceLevel NULL");
        }
    }

    public DanceStyle getDanceStyle() {
        return danceStyle;
    }

    public void setDanceStyle(DanceStyle danceStyle) throws Exception {
        if(this.danceStyle == null) {
            if (danceStyle != null) {
                this.danceStyle = danceStyle;
                danceStyle.addGroup(this);
            } else {
                throw new Exception("danceStyle NULL");
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) throws Exception {
        if(this.location == null) {
            if (location != null) {
                this.location = location;
                location.addGroup(this);
            } else {
                throw new Exception("location NULL");
            }
        }
    }

    public void replaceLocation(Location location) throws Exception {
        if(location != null) {
            this.location.removeGroup(this);
            this.location = null;
            setLocation(location);
        }else {
            throw new Exception("location NULL");
        }
    }

    public Set<EmployeeInstructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<EmployeeInstructor> instructors) throws Exception {
        if(instructors != null) {
            if(instructors.size() >= 1) {
                if(this.instructors != null) {
                    this.instructors.forEach(instr -> {
                        instr.removeGroup(this);
                    });
                }
                this.instructors = instructors;
                instructors.forEach(instr -> {
                    try {
                        instr.addGroup(this);
                    }catch(Exception e){
                    }
                });
            }else{
                throw new Exception("instructors size < 1");
            }
        }else{
            throw new Exception("instructors NULL");
        }
    }

    public void removeInstrucotr(EmployeeInstructor instructor){
        if(this.instructors.contains(instructor)){
            this.instructors.remove(instructor);
        }
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) throws Exception {
        if(!this.students.contains(student)) {
            if (student != null) {
                this.students.add(student);
                student.addGroup(this);
            } else {
                throw new Exception("student NULL");
            }
        }
    }

    public void removeStudent(Student student){
        if(this.students.contains(student)){
            this.students.remove(student);
            student.removeGroup(this);
        }
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void addAnnouncement(Announcement announcement) throws Exception {
        if(!this.announcements.contains(announcement)) {
            if (announcement != null) {
                this.announcements.add(announcement);
                announcement.setGroup(this);
            } else {
                throw new Exception("announcement NULL");
            }
        }
    }
}
