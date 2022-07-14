package mr.danceschool.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAll(Sort sort);

    Location findByAdress(String adress);
}
