package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAll();
}

