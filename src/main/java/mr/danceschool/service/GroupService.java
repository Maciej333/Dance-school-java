package mr.danceschool.service;

import mr.danceschool.entity.Group;
import mr.danceschool.entity.GroupChoreo;
import mr.danceschool.utils.GroupStatus;

import java.util.List;

public interface GroupService {

    public Group save(Group group);

    public Group findById(long id);

    public List<Group> findAll();

    public GroupChoreo findChoreoByName(String name);

    public Integer deleteById(long id);

    List<Group> findByGroupStatus(GroupStatus groupStatus);
}
