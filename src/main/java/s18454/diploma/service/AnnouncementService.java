package s18454.diploma.service;

import s18454.diploma.entity.Announcement;
import s18454.diploma.entity.Group;

import java.util.List;

public interface AnnouncementService {

    public Announcement save(Announcement announcement);

    public Announcement findById(long id);

    public List<Announcement> findAll();

    public Integer deleteById(long id);

    List<Announcement> findByGroup(Group group);
}
