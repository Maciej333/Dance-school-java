package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.GroupChoreo;

import java.util.List;

public interface GroupChoreoRepository  extends JpaRepository<GroupChoreo, Long> {

    List<GroupChoreo> findAll();

    GroupChoreo findByName(String name);
}
