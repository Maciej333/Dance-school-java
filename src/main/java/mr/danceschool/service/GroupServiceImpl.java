package mr.danceschool.service;

import mr.danceschool.dao.GroupChoreoRepository;
import mr.danceschool.dao.GroupRepository;
import mr.danceschool.entity.Group;
import mr.danceschool.entity.GroupChoreo;
import mr.danceschool.utils.GroupStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private GroupChoreoRepository groupChoreoRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupChoreoRepository groupChoreoRepository) {
        this.groupRepository = groupRepository;
        this.groupChoreoRepository = groupChoreoRepository;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group findById(long id) {
        Optional<Group> group = groupRepository.findById(id);
        if(group.isPresent()){
            return group.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public GroupChoreo findChoreoByName(String name) {
        return groupChoreoRepository.findByName(name);
    }

    @Override
    public Integer deleteById(long id) {
        groupRepository.deleteById(id);
        return 1;
    }

    @Override
    public List<Group> findByGroupStatus(GroupStatus groupStatus) {
        return groupRepository.findByGroupStatus(groupStatus);
    }
}
