package s18454.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import s18454.diploma.dao.LocationRepository;
import s18454.diploma.entity.Location;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location findById(long id) {
        Optional<Location> location = locationRepository.findById(id);
        if(location.isPresent()){
            return location.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll(Sort.by(Sort.Direction.ASC, "adress"));
    }

    @Override
    public Integer deleteById(long id) {
        locationRepository.deleteById(id);
        return 1;
    }

    @Override
    public Location findByAdress(String adress) {
        return locationRepository.findByAdress(adress);
    }
}
