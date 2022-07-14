package s18454.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s18454.diploma.controller.controllerModel.ResponseMsg;
import s18454.diploma.entity.*;
import s18454.diploma.service.DanceStyleService;
import s18454.diploma.service.StudentService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dance_style")
@CrossOrigin(origins = "http://localhost:3000")
public class DanceStyleController {

    private DanceStyleService danceStyleService;
    private StudentService studentService;

    @Autowired
    public DanceStyleController(DanceStyleService danceStyleService, StudentService studentService) {
        this.danceStyleService = danceStyleService;
        this.studentService = studentService;
    }

    @PostMapping("/save")
    public ResponseMsg saveDanceStyle(@RequestBody DanceStyle danceStyle){
        try {
            DanceStyle danceStyleName = danceStyleService.findByName(danceStyle.getName());
            if(danceStyleName != null){
                throw new Exception("UNIQUE");
            }
            List<Student> studentList = studentService.findAll();
            for(int i = 0; i < studentList.size(); i++){
                StudentDanceStyle studentDanceStyle = new StudentDanceStyle(studentList.get(i), danceStyle);
            }

            danceStyleService.save(danceStyle);
            return new ResponseMsg(200, "ADD");
        }catch(Exception e){
            System.err.println("[Error] /api/dance_style/save | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @GetMapping("/get_all")
    public List<DanceStyle> getAllDanceStyles(){
        return danceStyleService.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DanceStyle> getDanceStyle(@PathVariable long id){
        DanceStyle danceStyle = danceStyleService.findById(id);
        if(danceStyle != null){
            return new ResponseEntity(danceStyle, HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/dance_style/get/{id} | danceStyle null");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseMsg updateDanceStyle(@PathVariable long id, @RequestBody DanceStyle ds){
        try{
            DanceStyle danceStyle = danceStyleService.findById(id);
            DanceStyle danceStyleName = danceStyleService.findByName(ds.getName());
            if(danceStyleName != null){
                if(danceStyleName.getId() != danceStyle.getId()) {
                    throw new Exception("UNIQUE");
                }
            }
            danceStyle.setName(ds.getName());
            danceStyle.setCountryOfOrigin(ds.getCountryOfOrigin());
            danceStyle.setDescription(ds.getDescription());

            danceStyleService.save(danceStyle);
            return new ResponseMsg(200, "UPDATE");
        }catch(Exception e){
            System.err.println("[Error] /api/dance_style/update/{id} | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDanceStyle(@PathVariable long id){
        DanceStyle danceStyle = danceStyleService.findById(id);
        if(danceStyle != null){
            Set<Group> groups = danceStyle.getGroups();
            groups.forEach(g -> {
                Set<Student> students = g.getStudents();
                students.forEach(s -> {
                    s.removeGroup(g);
                });
            });

            danceStyleService.deleteById(id);
            return new ResponseEntity("DELETE", HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/dance_style/delete/{id} | danceStyle null");
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }
}
