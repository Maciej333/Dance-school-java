package s18454.diploma.service;

import s18454.diploma.entity.Group;
import s18454.diploma.entity.GroupChoreo;
import s18454.diploma.utils.GroupStatus;

import java.util.List;

public interface GroupService {

    public Group save(Group group);

    public Group findById(long id);

    public List<Group> findAll();

    public GroupChoreo findChoreoByName(String name);

    public Integer deleteById(long id);

    List<Group> findByGroupStatus(GroupStatus groupStatus);
}
