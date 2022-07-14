package s18454.diploma.service;

import s18454.diploma.entity.Location;

import java.util.List;

public interface LocationService {

    public Location save(Location location);

    public Location findById(long id);

    public List<Location> findAll();

    public Integer deleteById(long id);

    Location findByAdress(String adress);
}
