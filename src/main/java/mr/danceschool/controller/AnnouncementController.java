package s18454.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s18454.diploma.entity.Announcement;
import s18454.diploma.entity.Group;
import s18454.diploma.service.AnnouncementService;
import s18454.diploma.service.GroupService;

import java.util.*;

@RestController
@RequestMapping("/api/announcement")
@CrossOrigin(origins = "http://localhost:3000")
public class AnnouncementController {

    private AnnouncementService announcementService;
    private GroupService groupService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, GroupService groupService) {
        this.announcementService = announcementService;
        this.groupService = groupService;
    }

    @PostMapping("/add_school_ann")
    public ResponseEntity<String> saveSchoolAnnouncement(@RequestBody Announcement ann){
        try{
            Announcement announcement = new Announcement(ann.getText());
            announcementService.save(announcement);

            return new ResponseEntity("ADD", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/announcement/add_school_ann | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_group_ann/{groupId}")
    public ResponseEntity<String> saveGroupAnnouncement(@PathVariable long groupId, @RequestBody Announcement ann){
        try{
            Group group = groupService.findById(groupId);
            Announcement announcement = new Announcement(ann.getText());
            group.addAnnouncement(announcement);

            announcementService.save(announcement);
            return new ResponseEntity("ADD", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/announcement/add_group_ann/{groupId} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_school_anns")
    public List<Announcement> getSchoolAnnouncements(){
        return announcementService.findByGroup(null);
    }

    @GetMapping("/get_group_anns/{groupId}")
    public ResponseEntity<List<Announcement>> getGroupAnnouncements(@PathVariable long groupId){
        try{
            Group group = groupService.findById(groupId);
            return new ResponseEntity(group.getAnnouncements().stream().sorted(Comparator.comparing(Announcement::getDate).reversed()).toList(), HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/announcement/get_group_anns/{groupId} | "+e.getMessage());
            return new ResponseEntity(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_ann/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable long id){
        Announcement announcement = announcementService.findById(id);
        if(announcement != null){
            return new ResponseEntity(announcement, HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/announcement/get_ann/{id} | ");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_ann/{id}")
    public ResponseEntity<String> updateAnnouncement(@PathVariable long id, @RequestBody Announcement a){
        try{
            Announcement announcement = announcementService.findById(id);
            announcement.setText(a.getText());

            announcementService.save(announcement);
            return new ResponseEntity("UPDATE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/announcement/update_ann/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_ann/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable long id){
        try{
            announcementService.deleteById(id);

            return new ResponseEntity("DELETE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/announcement/delete_ann/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }
}
