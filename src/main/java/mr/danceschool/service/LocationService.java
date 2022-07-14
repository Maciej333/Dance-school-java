package mr.danceschool.service;

import mr.danceschool.entity.Location;

import java.util.List;

public interface LocationService {

    public Location save(Location location);

    public Location findById(long id);

    public List<Location> findAll();

    public Integer deleteById(long id);

    Location findByAdress(String adress);
}
