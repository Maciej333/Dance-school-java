package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.*;
import s18454.diploma.utils.DanceLevel;

import javax.persistence.*;

@Entity
@Table(name = "Student_dance_style")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({ "student" })
public class StudentDanceStyle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "level", nullable = false)
    private DanceLevel level;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="dance_style_id")
    private DanceStyle danceStyle;

    public StudentDanceStyle() {
    }

    public StudentDanceStyle(Student student, DanceStyle danceStyle) throws Exception {
        setStudent(student);
        setDanceStyle(danceStyle);
        setLevel(DanceLevel.P0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) throws Exception {
        if(this.student == null) {
            if (student != null) {
                this.student = student;
                student.addStudentDanceStyle(this);
            } else {
                throw new Exception("student null");
            }
        }
    }

    public DanceStyle getDanceStyle() {
        return danceStyle;
    }

    public void setDanceStyle(DanceStyle danceStyle) throws Exception {
        if(this.danceStyle == null) {
            if (danceStyle != null) {
                this.danceStyle = danceStyle;
                danceStyle.addStudentDanceStyle(this);
            } else {
                throw new Exception("danceStyle null");
            }
        }
    }

    public DanceLevel getLevel() {
        return level;
    }

    public void setLevel(DanceLevel level){
        if(level != null) {
            if(this.level != null) {
//                this.level = level;

                // nie mozna ustawić mniejszego poziomu
                System.out.println("this.level.compareTo(level) = " + this.level.compareTo(level));
                if (this.level.compareTo(level) < 0) {
                    this.level = level;
                }
            }else{
                this.level = level;
            }
        }
    }

    @Override
    public String toString() {
        return "StudentDanceStyle{" +
                "id=" + id +
                ", level=" + level +
                '}';
    }
}
