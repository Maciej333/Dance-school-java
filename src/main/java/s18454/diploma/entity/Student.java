package s18454.diploma.entity;

import com.fasterxml.jackson.annotation.*;
import s18454.diploma.utils.DanceLevel;
import s18454.diploma.utils.Gender;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "Student")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({ "groups" })
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "discount", nullable = false)
    private int discount = 0;

    private static int maxDiscount = 50;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "student")
    @JoinColumn(name = "user_pass_id")
    private Pass pass;

    @OneToMany(mappedBy="student", cascade = CascadeType.ALL)
    private Set<StudentDanceStyle> studentDanceStyleList = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "group_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    public Student() {
    }

    private Student(int discount, Gender gender) throws Exception {
        setDiscount(discount);
        setEmail(null);
        setGender(gender);
    }

    private Student(int discount, String email, Gender gender) throws Exception {
        setDiscount(discount);
        setEmail(email);
        setGender(gender);
    }

    public static Student createStudent(User user, int discount, Gender gender ) throws Exception {
        if(user == null){
            throw new Exception("createStudent - user null");
        }else{
            Student student = new Student( discount, gender);
            Pass.createPass(student);
            student.setUser(user);
            return student;
        }
    }

    public static Student createStudent(User user, int discount, String email, Gender gender ) throws Exception {
        if(user == null){
            throw new Exception("createStudent - user null");
        }else{
            Student student = new Student(discount, email, gender);
            Pass.createPass(student);
            student.setUser(user);
            return student;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) throws Exception{
        if(discount >= 0 && discount <= maxDiscount) {
            this.discount = discount;
        }else{
            throw new Exception("discount from 0 to maxDiscount");
        }
    }

    public static int getMaxDiscount() {
        return maxDiscount;
    }

    public static void setMaxDiscount(int maxDiscount) throws Exception {
        if(maxDiscount > 0) {
            Student.maxDiscount = maxDiscount;
        }else{
            throw new Exception("maxDiscount <= 0");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(email == null) {
            this.email = email;
        }else{
            Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if(matcher.find()) {
                this.email = email;
            }else {
                throw new Exception("email regex");
            }
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) throws Exception {
        if(gender != null) {
            this.gender = gender;
        }else{
            throw new Exception("gender NULL");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws Exception {
        if(this.user == null) {
            if (user != null) {
                this.user = user;
                user.setStudent(this);
            } else {
                throw new Exception("user NULL");
            }
        }
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) throws Exception {
        if(this.pass == null) {
            if (pass != null) {
                this.pass = pass;
                pass.setStudent(this);
            } else {
                throw new Exception("pass NULL");
            }
        }
    }

    public Set<StudentDanceStyle> getStudentDanceStyleList() {
        return studentDanceStyleList;
    }

    public void addStudentDanceStyle(StudentDanceStyle sds) throws Exception{
        if(!this.studentDanceStyleList.contains(sds)) {
            if (sds != null) {
                studentDanceStyleList.add(sds);
                sds.setStudent(this);
            } else {
                throw new Exception("StudentDanceStyle NULL");
            }
        }
    }

    //update level
    public void updateDanceStyleLevel(DanceStyle style, DanceLevel level){
        this.studentDanceStyleList.forEach(sds -> {
            if(sds.getDanceStyle() == style){
                sds.setLevel(level);
            }
        });
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) throws Exception {
        if(!this.groups.contains(group)) {
            if (group != null) {
                this.groups.add(group);
                group.addStudent(this);
            } else {
                throw new Exception("group NULL");
            }
        }
    }

    public void removeGroup(Group group){
        if(this.groups.contains(group)){
            this.groups.remove(group);
            group.removeStudent(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", discount=" + discount +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
