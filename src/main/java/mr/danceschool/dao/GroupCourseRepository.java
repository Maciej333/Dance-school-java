package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.GroupCourse;

import java.util.List;

public interface GroupCourseRepository  extends JpaRepository<GroupCourse, Long> {

    List<GroupCourse> findAll();
}

