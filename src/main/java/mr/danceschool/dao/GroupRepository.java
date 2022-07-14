package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.Group;
import mr.danceschool.utils.GroupStatus;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Group, Long> {

    List<Group> findAll();

    List<Group> findByGroupStatus(GroupStatus groupStatus);
}
