package mr.danceschool.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mr.danceschool.utils.DanceLevel;
import mr.danceschool.utils.Gender;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Group_course")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class GroupCourse extends Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "classroom_day", nullable = false)
    private DayOfWeek classroomDay;

    @Column(name = "classroom_start_time", nullable = false)
    private LocalTime classroomStartTime;

    @Column(name = "classroom_duration", nullable = false)
    private int classroomDuration;

    public GroupCourse() {
    }

    public GroupCourse(Date createDate, List<Gender> genderList, DanceLevel danceLevel, DanceStyle danceStyle, Location location, Set<EmployeeInstructor> instructors, DayOfWeek classroomDay, LocalTime classroomStartTime, int classroomDuration) throws Exception {
        super(createDate, genderList, danceLevel, danceStyle, location, instructors);
        setClassroomDay(classroomDay);
        setClassroomStartTime(classroomStartTime);
        setClassroomDuration(classroomDuration);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DayOfWeek getClassroomDay() {
        return classroomDay;
    }

    public void setClassroomDay(DayOfWeek classroomDay) throws Exception {
        if(classroomDay != null) {
            this.classroomDay = classroomDay;
        }else{
            throw new Exception("classroomDay NULL");
        }
    }

    public LocalTime getClassroomStartTime() {
        return classroomStartTime;
    }

    public void setClassroomStartTime(LocalTime classroomStartTime) throws Exception {
        if(classroomStartTime != null) {
            this.classroomStartTime = classroomStartTime;
        }else{
            throw new Exception("classroomStartTime NULL");
        }
    }

    public int getClassroomDuration() {
        return classroomDuration;
    }

    public void setClassroomDuration(int classroomDuration) throws Exception {
        if(classroomDuration > 0) {
            this.classroomDuration = classroomDuration;
        }else{
            throw new Exception("classroomDuration <= 0");
        }
    }

    @Override
    public String toString() {
        return "GroupCourse{" +
                "id=" + id +
                ", classroomDay=" + classroomDay +
                ", classroomStartTime=" + classroomStartTime +
                ", classroomDuration=" + classroomDuration +
                '}';
    }
}
