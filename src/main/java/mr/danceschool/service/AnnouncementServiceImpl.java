package mr.danceschool.service;

import mr.danceschool.dao.AnnouncementRepository;
import mr.danceschool.entity.Announcement;
import mr.danceschool.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement findById(long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if(announcement.isPresent()){
            return announcement.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Integer deleteById(long id) {
        announcementRepository.deleteById(id);
        return 1;
    }

    @Override
    public List<Announcement> findByGroup(Group group) {
        return announcementRepository.findByGroupOrderByDateDesc(group);
    }
}
