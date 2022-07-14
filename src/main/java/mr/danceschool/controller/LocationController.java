package s18454.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s18454.diploma.controller.controllerModel.ResponseMsg;
import s18454.diploma.entity.Location;
import s18454.diploma.service.LocationService;

import java.util.*;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/save_location")
    public ResponseMsg saveLocation(@RequestBody Location location){
        try {
            Location locationAdressFree = locationService.findByAdress(location.getAdress());
            if(locationAdressFree != null) {
                throw new Exception("UNIQUE");
            }

            locationService.save(location);
            return new ResponseMsg(200, "ADD");
        }catch(Exception e){
            System.err.println("[Error] /api/location/save_location | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @GetMapping("/get_location/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable long id){
        Location location = locationService.findById(id);
        if(location != null){
            return new ResponseEntity(location, HttpStatus.OK);
        }else{
            System.err.println("[Error] /api/location/get_location/{id} | ");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_all_locations")
    public List<Location> getAllLocations(){
        return locationService.findAll();
    }

    @PutMapping("/update_location/{id}")
    public ResponseMsg updateLocation(@PathVariable long id, @RequestBody Location l){
        try {
            Location location = locationService.findById(id);
            Location locationAdressFree = locationService.findByAdress(l.getAdress());
            if(locationAdressFree != null) {
                if(locationAdressFree.getId() != location.getId()) {
                    throw new Exception("UNIQUE");
                }
            }
            location.setAdress(l.getAdress());

            locationService.save(location);
            return new ResponseMsg(200, "UPDATE");
        }catch(Exception e){
            System.err.println("[Error] /api/location/update_location/{id} | "+e.getMessage());
            if(e.getMessage().equals("UNIQUE")){
                return new ResponseMsg(501, "ERROR UNIQUE");
            }
            return new ResponseMsg(500, "ERROR");
        }
    }

    @DeleteMapping("/delete_location/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable long id){
        try {
            Location location = locationService.findById(id);
             locationService.deleteById(id);
            return new ResponseEntity("DELETE", HttpStatus.OK);
        }catch(Exception e){
            System.err.println("[Error] /api/location/delete_location/{id} | "+e.getMessage());
            return new ResponseEntity("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

}
