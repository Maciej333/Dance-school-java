package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.EmployeeInstructor;

import java.util.List;

public interface EmployeeInstructorRepository extends JpaRepository<EmployeeInstructor, Long> {

    List<EmployeeInstructor> findAll();
}
