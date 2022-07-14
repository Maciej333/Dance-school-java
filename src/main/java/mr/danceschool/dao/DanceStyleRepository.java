package mr.danceschool.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.DanceStyle;

import java.util.List;

public interface DanceStyleRepository extends JpaRepository<DanceStyle, Long> {

    List<DanceStyle> findAll(Sort sort);

    DanceStyle findByName(String name);
}
