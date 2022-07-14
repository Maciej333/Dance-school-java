package mr.danceschool.service;

import mr.danceschool.entity.Announcement;
import mr.danceschool.entity.Group;

import java.util.List;

public interface AnnouncementService {

    public Announcement save(Announcement announcement);

    public Announcement findById(long id);

    public List<Announcement> findAll();

    public Integer deleteById(long id);

    List<Announcement> findByGroup(Group group);
}
