package s18454.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s18454.diploma.entity.*;
import s18454.diploma.service.*;
import s18454.diploma.utils.Gender;
import s18454.diploma.utils.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/init")
@CrossOrigin(origins = "http://localhost:3000")
public class _InitController {

    private BCryptPasswordEncoder passwordEncoder;

    private AnnouncementService announcementService;
    private DanceStyleService danceStyleService;
    private LocationService locationService;
    private UserService userService;

    @Autowired
    public _InitController(AnnouncementService announcementService, DanceStyleService danceStyleService, GroupService groupService, EmployeeService employeeService, LocationService locationService, StudentService studentService, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.announcementService = announcementService;
        this.danceStyleService = danceStyleService;
        this.locationService = locationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/populate")
    public void getTests(){
        try {
            List<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add("000-ZZZ-ZZZ");
            User dyrektor = new User("admin", passwordEncoder.encode("admin"), "Dyrektor", "Maciej", phoneNumbers, new Date(), Role.DIRECTOR);
            userService.save(dyrektor);

            phoneNumbers = new ArrayList<>();
            phoneNumbers.add("221-ZZZ-ZZZ");
            User instruktor1 = new User("ins001", passwordEncoder.encode("ins001"), "Instruktor", "Aga", phoneNumbers, new Date(), Role.INSTRUCTOR);
            userService.save(instruktor1);

            phoneNumbers = new ArrayList<>();
            phoneNumbers.add("222-ZZZ-ZZZ");
            User instruktor2 = new User("ins002", passwordEncoder.encode("ins002"), "Instruktor", "Marta", phoneNumbers, new Date(), Role.INSTRUCTOR);
            userService.save(instruktor2);

            phoneNumbers = new ArrayList<>();
            phoneNumbers.add("331-ZZZ-ZZZ");
            User student1 = new User("stu001", passwordEncoder.encode("stu001"), "Student", "Paulina", 0, Gender.FEMALE);
            userService.save(student1);

            phoneNumbers = new ArrayList<>();
            phoneNumbers.add("332-ZZZ-ZZZ");
            User student2 = new User("stu002", passwordEncoder.encode("stu002"), "Student", "Paweł", 0, Gender.MALE);
            userService.save(student2);

            Location location1 = new Location("Warszawa Wola");
            Location location2 = new Location("Warszawa Ochota");
            locationService.save(location1);
            locationService.save(location2);

            DanceStyle salsa = new DanceStyle("Salsa", "Kuba", "desc");
            StudentDanceStyle sds1 = new StudentDanceStyle(student1.getStudent(), salsa);
            StudentDanceStyle sds2 = new StudentDanceStyle(student2.getStudent(), salsa);
            danceStyleService.save(salsa);

            Announcement schoolAnn = new Announcement("School start!");
            announcementService.save(schoolAnn);

        }catch(Exception e){
            System.err.println("[ERROR - InitController] | /api/init/populate | "+e.getMessage());
        }
    }
}
