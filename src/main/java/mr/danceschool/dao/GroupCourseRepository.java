package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.GroupCourse;

import java.util.List;

public interface GroupCourseRepository  extends JpaRepository<GroupCourse, Long> {

    List<GroupCourse> findAll();
}

