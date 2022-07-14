package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.GroupChoreo;

import java.util.List;

public interface GroupChoreoRepository  extends JpaRepository<GroupChoreo, Long> {

    List<GroupChoreo> findAll();

    GroupChoreo findByName(String name);
}
