package s18454.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s18454.diploma.entity.EmployeeInstructor;
import s18454.diploma.entity.Group;
import s18454.diploma.entity.GroupCourse;
import s18454.diploma.service.EmployeeService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {

    private EmployeeService employeeService;

    @Autowired
    public InstructorController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/get_groups/{id}")
    public ResponseEntity<Set<Group>> getInstructorGroup(@PathVariable long id){
        try{
            EmployeeInstructor employeeInstructor = (EmployeeInstructor)employeeService.findById(id);

            return new ResponseEntity(employeeInstructor.getGroups(), HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/instructor/get_groups/{id} | "+e.getMessage());
            return new ResponseEntity(new HashSet<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/today_groups/{instructorId}")
    public ResponseEntity<List<Group>> getTodayGroups(@PathVariable long instructorId){
        try {
            EmployeeInstructor instructor = (EmployeeInstructor)employeeService.findById(instructorId);
            if(instructor == null){
                throw new Exception("instructor null");
            }
            DayOfWeek today = LocalDate.now().getDayOfWeek();
            List<GroupCourse> todayGroups = instructor.getGroups().stream()
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

//            Calendar presentDay = Calendar.getInstance();
//            List<Classroom> classrooms = new ArrayList<>();
//            instructor.getGroups().forEach(g -> {
//                g.getClassrooms().forEach(c -> {
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(c.getDate());
//                    if(presentDay.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && presentDay.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && presentDay.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)){
//                        classrooms.add(c);
//                    }
//                });
//            });
//            return classrooms.stream().sorted(Comparator.comparing(Classroom::getStartTime)).toList();
        }catch(Exception e){
            System.err.println("[Error] /api/instructor/today_groups/{instructorId} | "+e.getMessage());
            return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}
