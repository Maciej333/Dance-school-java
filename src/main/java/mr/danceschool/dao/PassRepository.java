package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.Pass;

public interface PassRepository extends JpaRepository<Pass, Long> {
}
