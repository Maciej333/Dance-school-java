package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAll();
}

