package mr.danceschool.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import mr.danceschool.controller.controllerModel.ResponseMsg;
import mr.danceschool.controller.controllerModel.UserEmployeeModel;
import mr.danceschool.controller.controllerModel.UserStudnetModel;
import mr.danceschool.entity.*;
import mr.danceschool.service.DanceStyleService;
import mr.danceschool.service.EmployeeService;
import mr.danceschool.service.StudentService;
import mr.danceschool.service.UserService;
import mr.danceschool.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import mr.danceschool.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private BCryptPasswordEncoder passwordEncoder;

    @Value( "${diploma.app.jwtSecret}" )
    private String jwtSecret;

    @Value( "${diploma.app.jwtExpirationMs}" )
    private long jwtExpirationDate;

    private UserService userService;
    private StudentService studentService;
    private EmployeeService employeeService;
    private DanceStyleService danceStyleService;

    @Autowired
    public UserController(UserService userService, StudentService studentService, EmployeeService employeeService, DanceStyleService danceStyleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.danceStyleService = danceStyleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/save_student")
    public ResponseMsg saveStudent(@RequestBody UserStudnetModel usm) {
        try {
            User userLoginFree = userService.findByLogin(usm.getLogin());
            if(userLoginFree != null){
                throw new Exception("UNIQUE");
            }

            User user;
            if(usm.getEmail() != null){
                user = new User(usm.getLogin(), passwordEncoder.encode(usm.getPassword()), usm.getFirstname(), usm.getLastname(), usm.getDiscount(), usm.getEmail(), usm.getGender());
            }else {
                user = new User(usm.getLogin(), passwordEncoder.encode(usm.getPassword()), usm.getFirstname(), usm.getLastname(), usm.getDiscount(), usm.getGender());
            }
            List<DanceStyle> danceStyles = danceStyleService.findAll();
            for(int i = 0 ; i< danceStyles.size(); i++){
                StudentDanceStyle studentDanceStyle = new StudentDanceStyle(user.getStudent(), danceStyles.get(i));
            }

            this.userService.save(user);
            return new ResponseMsg(200, "ADD");
        }catch(Exception e){
            System.err.println("[Error] /api/user/save_student | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }else {
                return new ResponseMsg(500, "ERROR");
            }
        }
    }

    @PostMapping("/save_employee")
    public ResponseMsg saveEmployee(@RequestBody UserEmployeeModel uem) {
        try {
            User userLoginFree = userService.findByLogin(uem.getLogin());
            if(userLoginFree != null){
                throw new Exception("UNIQUE");
            }
            User user = new User(uem.getLogin(), passwordEncoder.encode(uem.getPassword()), uem.getFirstname(), uem.getLastname(), uem.getPhoneNumers(), uem.getHiredDate(), uem.getRole());

            this.userService.save(user);
            return new ResponseMsg(200, "ADD");
        }catch(Exception e){
            System.err.println("[Error] /api/user/save_employee | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }else {
                return new ResponseMsg(500, "ERROR");
            }
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = this.userService.findById(id);
        if(user != null){
            return new ResponseEntity(user, HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/user/get/{id} | user null");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_employees")
    public List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/get_instructors")
    public List<EmployeeInstructor> getAllInstructors(){
        return employeeService.findAllInstructors();
    }

    @PutMapping("/update_employee/{id}")
    public ResponseMsg updateEmployee(@PathVariable long id, @RequestBody UserEmployeeModel uem) {
        User user = this.userService.findById(id);
        if(user != null) {
            try {
                User userLoginFree = userService.findByLogin(uem.getLogin());
                if(userLoginFree != null){
                    if(userLoginFree.getId() != user.getId()) {
                        throw new Exception("UNIQUE");
                    }
                }
                user.setLogin(uem.getLogin());
                user.setFirstname(uem.getFirstname());
                user.setLastname(uem.getLastname());
                user.getEmployee().setPhoneNumers(uem.getPhoneNumers());

                this.userService.save(user);
                return new ResponseMsg(200, "UPDATE");
            }catch(Exception e){
                System.err.println("[Error] /api/user/update_employee/{id} | "+e.getMessage());
                if(e.getMessage().equals("UNIQUE")){
                    return new ResponseMsg(501, "ERROR UNIQUE");
                }
                return new ResponseMsg(500, "ERROR");
            }
        }else{
            System.err.println("[Error] /api/user/update_employee/{id} | user null");
            return new ResponseMsg(500, "ERROR");
        }
    }

    @PutMapping("/update_student/{id}")
    public ResponseMsg updateStudent(@PathVariable long id, @RequestBody UserStudnetModel usm) {
        User user = this.userService.findById(id);
        if(user != null) {
            try {
                User userLoginFree = userService.findByLogin(usm.getLogin());
                if(userLoginFree != null){
                    if(userLoginFree.getId() != user.getId()) {
                        throw new Exception("UNIQUE");
                    }
                }
                user.setLogin(usm.getLogin());
                user.setFirstname(usm.getFirstname());
                user.setLastname(usm.getLastname());
                user.getStudent().setEmail(usm.getEmail());
                user.getStudent().setGender(usm.getGender());

                this.userService.save(user);
                return new ResponseMsg(200, "UPDATE");
            }catch(Exception e){
                System.err.println("[Error] /api/user/update_student/{id} | "+e.getMessage());
                if(e.getMessage().equals("UNIQUE")){
                    return new ResponseMsg(501, "ERROR UNIQUE");
                }
                return new ResponseMsg(500, "ERROR");
            }
        }else{
            System.err.println("[Error] /api/user/update_student/{id} | user null");
            return new ResponseMsg(500, "ERROR");
        }
    }

    @PutMapping("/add_student/{id}")
    public ResponseMsg addStudentToEmployee(@PathVariable long id, @RequestBody Student s){
        User user = this.userService.findById(id);
        if(user != null){
            if(user.getStudent() == null && user.getEmployee() != null) {
                try {
                    Student student = Student.createStudent(user, s.getDiscount(), s.getEmail(), s.getGender());

                    List<DanceStyle> danceStyles = danceStyleService.findAll();
                    for(int i = 0 ; i < danceStyles.size(); i++){
                        StudentDanceStyle studentDanceStyle = new StudentDanceStyle(student, danceStyles.get(i));
                    }

                    this.studentService.save(student);
                    return new ResponseMsg(200, "ADD");
                }catch(Exception e) {
                    System.err.println("[Error] /api/user/add_student/{id} | "+e.getMessage());
                    return new ResponseMsg(501, "ERROR create student");
                }
            }else{
                System.err.println("[Error] /api/user/add_student/{id} | student NULL, employee NOT NULL");
                return new ResponseMsg(500, "ERROR");
            }
        }else{
            System.err.println("[Error] /api/user/add_student/{id} | user null");
            return new ResponseMsg(500, "ERROR");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        User user = this.userService.findById(id);
        if(user != null) {
            if(user.getEmployee() != null) {        // dodana linia
                try {
                    EmployeeInstructor instructor = (EmployeeInstructor) user.getEmployee();
                    Set<Group> groups = instructor.getGroups();
                    groups.forEach(g -> {
                        instructor.removeGroup(g);
                        g.removeInstrucotr(instructor);
                    });
                }catch(Exception e){
                    System.err.println("[Error] /api/user/delete/{id} | employee group delete | "+e.getMessage());
                }
            }
            this.userService.deleteById(id);
            return new ResponseEntity("DELETE", HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/user/delete/{id} | user null");
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change_password/{id}")
    public ResponseEntity<String> changeUserPassword(@PathVariable long id, @RequestBody String password){
        try {
            User user = userService.findById(id);
            String newPassword = password.split("\"")[1];
            user.setPassword(passwordEncoder.encode(newPassword));

            userService.save(user);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/user/change_password/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/refresh")
    public void refreshUserToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String login = decodedJWT.getSubject();
                User user = userService.findByLogin(login);
                String accessToken = JWT.create()
                        .withSubject(user.getLogin())
                        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationDate))
                        .withClaim("roles", user.getRoles().stream().map(r -> r.name()).toList())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("id", user.getId()+"");
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch(Exception e){
                System.err.println("[Error] /api/user/refresh | "+ e.getMessage());

                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }catch(IOException ioe){
                    System.err.println("[Error] /api/user/refresh |  IOException  | "+ioe.getMessage());
                }
            }
        }else{
            System.err.println("[Error] /api/user/refresh |  NO REFRESH TOKEN");
        }
    }

    @PutMapping("/delete_employee_student/{id}")
    public ResponseEntity<String> deleteEmployeeStudent(@PathVariable long id){
        try {
            User user = userService.findById(id);
            Student student = user.getStudent();
            user.removeStudent();
            user.setRoles(user.getRoles().stream().filter(r -> {
                if(r.equals(Role.STUDENT)){
                    return false;
                }
                return true;
            }).collect(Collectors.toSet()));

            userService.save(user);
            studentService.deleteById(student.getId());
            return new ResponseEntity( "DELETE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/user/delete_employee_student/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

}

