package mr.danceschool.controller;

import mr.danceschool.controller.controllerModel.GroupChoreoModel;
import mr.danceschool.controller.controllerModel.GroupCourseModel;
import mr.danceschool.controller.controllerModel.ResponseMsg;
import mr.danceschool.entity.*;
import mr.danceschool.service.*;
import mr.danceschool.utils.DanceLevel;
import mr.danceschool.utils.GroupStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mr.danceschool.controller.controllerModel.*;
import mr.danceschool.entity.*;
import mr.danceschool.service.*;

import java.util.*;

@RestController
@RequestMapping("/api/group")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    private GroupService groupService;
    private EmployeeService employeeService;
    private StudentService studentService;
    private DanceStyleService danceStyleService;
    private LocationService locationService;

    @Autowired
    public GroupController(GroupService groupService, EmployeeService employeeService, StudentService studentService, DanceStyleService danceStyleService, LocationService locationService) {
        this.groupService = groupService;
        this.employeeService = employeeService;
        this.studentService = studentService;
        this.danceStyleService = danceStyleService;
        this.locationService = locationService;
    }

    @PostMapping("/save_course")
    public ResponseEntity<String> saveGroupCourse(@RequestBody GroupCourseModel gcm){
        try{
            DanceStyle danceStyle = danceStyleService.findById(gcm.getDanceStyleId());
            Location location = locationService.findById(gcm.getLocationId());
            Set<EmployeeInstructor> employeeInstructors = new HashSet<>();
            gcm.getInstructorIds().forEach(instrId -> {
                EmployeeInstructor instructor = (EmployeeInstructor)employeeService.findById(instrId);
                employeeInstructors.add(instructor);
            });
            Group group = Group.createGroup(new Date(), gcm.getGenderList(), gcm.getDanceLevel(), danceStyle, location, employeeInstructors, gcm.getClassroomDay(), gcm.getClassroomStartTime(), gcm.getClassroomDuration());

            groupService.save(group);
            return new ResponseEntity("ADD", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/save_course | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save_choreo")
    public ResponseMsg saveGroupChoreo(@RequestBody GroupChoreoModel gcm){
        try{
            GroupChoreo groupChoreo = groupService.findChoreoByName(gcm.getName());
            if(groupChoreo != null){
                throw new Exception("UNIQUE");
            }
            DanceStyle danceStyle = danceStyleService.findById(gcm.getDanceStyleId());
            Location location = locationService.findById(gcm.getLocationId());
            Set<EmployeeInstructor> employeeInstructors = new HashSet<>();
            gcm.getInstructorIds().forEach(instrId -> {
                EmployeeInstructor instructor = (EmployeeInstructor)employeeService.findById(instrId);
                employeeInstructors.add(instructor);
            });
            Group group = Group.createGroup(new Date(), gcm.getGenderList(), gcm.getDanceLevel(), danceStyle, location, employeeInstructors, gcm.getName());

            groupService.save(group);
            return new ResponseMsg(200, "ADD");
        }catch(Exception e){
            System.err.println("[Error] /api/group/save_choreo | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @GetMapping("/get_all")
    public List<Group> getAllGroups(){
        return groupService.findAll();
    }

    @GetMapping("/get_open")
    public List<Group> getOpenGroups(){
        return groupService.findByGroupStatus(GroupStatus.OPEN);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable long id){
        Group group = groupService.findById(id);
        if(group != null){
            return new ResponseEntity(group, HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/group/get/{id} | ");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_course/{id}")
    public ResponseEntity<String> updateGroupCourse(@PathVariable long id, @RequestBody GroupCourseModel gcm){
        try{
            GroupCourse groupCourse = (GroupCourse) groupService.findById(id);
            Location location = locationService.findById(gcm.getLocationId());
            groupCourse.setGenderList(gcm.getGenderList());
            groupCourse.replaceLocation(location);
            groupCourse.setClassroomDay(gcm.getClassroomDay());
            groupCourse.setClassroomStartTime(gcm.getClassroomStartTime());
            groupCourse.setClassroomDuration(gcm.getClassroomDuration());

            groupService.save(groupCourse);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/update_course/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_choreo/{id}")
    public ResponseMsg updateGroupChoreo(@PathVariable long id, @RequestBody GroupChoreoModel gcm){
        try{
            GroupChoreo groupChoreoName = groupService.findChoreoByName(gcm.getName());
            GroupChoreo groupChoreo = (GroupChoreo)groupService.findById(id);
            Location location = locationService.findById(gcm.getLocationId());

            if(groupChoreoName != null){
                if(groupChoreoName.getId() != groupChoreo.getId()) {
                    throw new Exception("UNIQUE");
                }
            }
            groupChoreo.setGenderList(gcm.getGenderList());
            groupChoreo.replaceLocation(location);
            groupChoreo.setName(gcm.getName());

            groupService.save(groupChoreo);
            return new ResponseMsg(200, "UPDATE");
        }catch(Exception e){
            System.err.println("[Error] /api/group/update_choreo/{id} | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable long id){
        try{
            Group group = groupService.findById(id);
            Set<Student> students = group.getStudents();
            students.forEach(s -> {
                s.removeGroup(group);
            });

            groupService.deleteById(id);
            return new ResponseEntity("DELETE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/get/{id} | ");
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/udpate_level/{groupId}")
    public ResponseEntity updateGroupLevel(@PathVariable long groupId, @RequestBody DanceLevel level){
        try{
            Group group = groupService.findById(groupId);
            group.setDanceLevel(level);

            // update students level
            group.getStudents().forEach(student -> {
                    student.updateDanceStyleLevel(group.getDanceStyle(), level);
            });

            groupService.save(group);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/udpate_level/{groupId} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/udpate_status/{groupId}")
    public ResponseEntity updateGroupStatus(@PathVariable long groupId, @RequestBody GroupStatus status){
        try{
            Group group = groupService.findById(groupId);
            group.setGroupStatus(status);

            groupService.save(group);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/udpate_status/{groupId} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/udpate_instructors/{groupId}")
    public ResponseEntity updateInstructorList(@PathVariable long groupId, @RequestBody Set<Long> instructorsIds){
        try{
            Group group = groupService.findById(groupId);
            Set<EmployeeInstructor> employeeInstructors = new HashSet<>();
            instructorsIds.forEach(instrId -> {
                EmployeeInstructor instructor = (EmployeeInstructor)employeeService.findById(instrId);
                employeeInstructors.add(instructor);
            });
            group.setInstructors(employeeInstructors);

            groupService.save(group);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/udpate_instructors/{groupId} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/group_students/{id}")
    public ResponseEntity<List<Student>> getGroupStudents(@PathVariable long id){
        try {
            Group group = groupService.findById(id);
            return new ResponseEntity(group.getStudents().stream().sorted(Comparator.comparing((Student s) -> s.getUser().getLastname())).toList(), HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/group_students/{id} | "+e.getMessage());
            return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check_group_student/{groupId}/{studentId}")
    public ResponseEntity<Boolean> checkGroupStudent(@PathVariable long groupId, @PathVariable long studentId){
        try {
            Group group = groupService.findById(groupId);
            List<Student> students = group.getStudents().stream()
                .filter(stu -> {
                    if(stu.getId() == studentId){
                        return true;
                    }
                    return false;
                })
                .toList();
            if(students.size() > 0){
                return new ResponseEntity(true, HttpStatus.OK);
            }
            return new ResponseEntity(false, HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/check_group_student/{groupId}/{studentId} | "+e.getMessage());
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check_group_instructor/{groupId}/{instructorId}")
    public ResponseEntity<Boolean> checkGroupInstructor(@PathVariable long groupId, @PathVariable long instructorId){
        try {
            Group group = groupService.findById(groupId);
            List<EmployeeInstructor> instructors = group.getInstructors().stream()
                    .filter(ins -> {
                        if(ins.getId() == instructorId){
                            return true;
                        }
                        return false;
                    })
                    .toList();
            if(instructors.size() > 0){
                return new ResponseEntity(true, HttpStatus.OK);
            }
            return new ResponseEntity(false, HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/group/check_group_instructor/{groupId}/{instructorId} | "+e.getMessage());
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

}
