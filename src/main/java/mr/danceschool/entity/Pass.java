package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "Pass")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Pass {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "number_of_entries", nullable = false)
    private int numberOfEntries = 0;

    private static int classroomCost = 30;

    @OneToOne
    private Student student;

    public Pass() {
    }

    public static Pass createPass(Student student) throws Exception{
        if(student == null){
            throw new Exception("createPass - student null");
        }else{
            Pass pass = new Pass();
            pass.setStudent(student);
            return pass;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) throws Exception {
        if(numberOfEntries >= 0) {
            this.numberOfEntries = numberOfEntries;
        }else{
            throw new Exception("numberOfEntries < 0");
        }
    }

    public void addNumberOfEntries(int entries) throws Exception{
        setNumberOfEntries(getNumberOfEntries() + entries);
    }

    public void minusOneNumberOfEntries() throws Exception {
        if(getNumberOfEntries() > 0) {
            setNumberOfEntries(getNumberOfEntries() - 1);
        } else {
            throw new Exception("numberOfEntries <= 0");
        }
    }

    public static int getClassroomCost() {
        return classroomCost;
    }

    public static void setClassroomCost(int classroomCost) throws Exception {
        if(classroomCost >= 0) {
            Pass.classroomCost = classroomCost;
        }else{
            throw new Exception("classroomCost < 0");
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) throws Exception {
        if(this.student == null) {
            if (student != null) {
                this.student = student;
                student.setPass(this);
            } else {
                throw new Exception("student NULL");
            }
        }
    }

    @Override
    public String toString() {
        return "Pass{" +
                "id=" + id +
                ", numberOfEntries=" + numberOfEntries +
                '}';
    }
}
