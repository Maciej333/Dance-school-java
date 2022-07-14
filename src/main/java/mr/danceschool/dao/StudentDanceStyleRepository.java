package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.StudentDanceStyle;

public interface StudentDanceStyleRepository extends JpaRepository<StudentDanceStyle, Long> {
}
