package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.Announcement;
import s18454.diploma.entity.Group;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByGroupOrderByDateDesc(Group group);
}
