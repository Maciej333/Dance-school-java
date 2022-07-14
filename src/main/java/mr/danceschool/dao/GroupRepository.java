package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.Group;
import s18454.diploma.utils.GroupStatus;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Group, Long> {

    List<Group> findAll();

    List<Group> findByGroupStatus(GroupStatus groupStatus);
}
