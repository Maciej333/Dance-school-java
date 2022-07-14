package mr.danceschool.controller;

import mr.danceschool.controller.controllerModel.ResponseMsg;
import mr.danceschool.entity.*;
import mr.danceschool.service.GroupService;
import mr.danceschool.service.PassService;
import mr.danceschool.service.StudentDanceStyleService;
import mr.danceschool.service.StudentService;
import mr.danceschool.utils.DanceLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mr.danceschool.entity.*;
import mr.danceschool.service.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private PassService passService;
    private StudentService studentService;
    private StudentDanceStyleService studentDanceStyleService;
    private GroupService groupService;

    @Autowired
    public StudentController(PassService passService, StudentService studentService, StudentDanceStyleService studentDanceStyleService, GroupService groupService) {
        this.passService = passService;
        this.studentService = studentService;
        this.studentDanceStyleService = studentDanceStyleService;
        this.groupService = groupService;
    }

    @GetMapping("/get_all")
    public List<Student> getAllStudents(){
        return studentService.findAll();
    }

    @GetMapping("/get_sds/{id}")
    public ResponseEntity<List<StudentDanceStyle>> getStudentDanceStylesLevels(@PathVariable long id){
        Student student = studentService.findById(id);
        if(student != null){
            return new ResponseEntity(student.getStudentDanceStyleList().stream().sorted(Comparator.comparing((StudentDanceStyle sds) -> sds.getDanceStyle().getName())).toList(), HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/student/get_sds/{id} | user null");
            return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_groups/{id}")
    public ResponseEntity<Set<Group>> getStudentGroups(@PathVariable long id){
        Student student = studentService.findById(id);
        if(student != null){
            return new ResponseEntity(student.getGroups(), HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/student/get_groups/{id} | user null");
            return new ResponseEntity(new HashSet<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity<String> setDiscount(@PathVariable long id, @RequestBody int discount){
        try {
            Student student = studentService.findById(id);
            student.setDiscount(discount);

            studentService.save(student);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/student/discount/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pass/{id}")
    public ResponseEntity<String> addNumberOfEntriesToPass(@PathVariable long id, @RequestBody int numberOfEntries){
        try {
            Pass pass = passService.findById(id);
            pass.addNumberOfEntries(numberOfEntries);

            passService.save(pass);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/student/pass/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/dance_level/{id}")
    public ResponseEntity<String> updateDanceStyleLevel(@PathVariable long id, @RequestBody DanceLevel level){
        try {
            StudentDanceStyle sds = studentDanceStyleService.findById(id);
            sds.setLevel(level);

            studentDanceStyleService.save(sds);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/student/dance_level/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/group/{groupId}")
    public ResponseMsg addToGroup(@PathVariable long groupId, @RequestBody long studentId) {
        try {
            Student student = studentService.findById(studentId);
            Group group = groupService.findById(groupId);
            if(!group.getGenderList().contains(student.getGender())){
                throw new Exception("GENDER");
            }


            //dodatkowe sprawdzenie poziomu dla choreo
            if(group instanceof GroupChoreo){
                final List<DanceLevel> studentLevel = new ArrayList();
                studentLevel.add(DanceLevel.P0);
                student.getStudentDanceStyleList().stream()
                        .forEach(sds -> {
                            if(sds.getDanceStyle() == group.getDanceStyle()){
                                studentLevel.set(0, sds.getLevel());
                            }
                        });

                if(group.getDanceLevel().compareTo(studentLevel.get(0)) > 0 ){
                    throw new Exception("LEVEL");
                }
            }


            student.addGroup(group);
            group.addStudent(student);

            studentService.save(student);
            return new ResponseMsg(200, "UPDATE");
        }catch(Exception e){
            System.err.println("[Error] /api/student/group/{groupId} | "+e.getMessage());
            if(e.getMessage().equals("LEVEL")){
                return new ResponseMsg(502, "ERROR LEVEL");
            }
            if(e.getMessage().equals("GENDER")){
                return new ResponseMsg(501, "ERROR GENDER");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @GetMapping("/pass/{studentId}")
    public ResponseEntity<Pass> getStudentPass(@PathVariable long studentId){
        try {
            Student student = studentService.findById(studentId);
            return new ResponseEntity(student.getPass(),HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/student/pass/{studentId} | user null");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/remove_from_group/{groupId}")
    public ResponseEntity<String> removeFromGroup(@PathVariable long groupId, @RequestBody long studentId){
        try {
            Group group = groupService.findById(groupId);
            Student student = studentService.findById(studentId);
            group.removeStudent(student);

            groupService.save(group);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/student/remove_from_group/{groupId} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/today_groups/{studentId}")
    public ResponseEntity<List<Group>> getTodayGroups(@PathVariable long studentId){
        try {
            Student student = studentService.findById(studentId);
            if(student == null){
                throw new Exception("student null");
            }
            DayOfWeek today = LocalDate.now().getDayOfWeek();
            List<GroupCourse> todayGroups = student.getGroups().stream()
                    .filter(g -> {
                        if(g instanceof GroupCourse){
                            return true;
                        }
                        return false;
                    })
                    .map(g -> {
                        return (GroupCourse)g;
                    })
                    .filter(g -> {
                        return ((GroupCourse) g).getClassroomDay() == today;
                    })
                    .sorted(Comparator.comparing(GroupCourse::getClassroomStartTime))
                    .toList();
            return new ResponseEntity(todayGroups , HttpStatus.OK);

        }catch(Exception e){
            System.err.println("[Error] /api/student/today_groups/{studentId} | "+e.getMessage());
            return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

}
