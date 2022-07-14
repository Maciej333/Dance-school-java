package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.EmployeeInstructor;

import java.util.List;

public interface EmployeeInstructorRepository extends JpaRepository<EmployeeInstructor, Long> {

    List<EmployeeInstructor> findAll();
}
