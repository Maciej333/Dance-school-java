package s18454.diploma.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.DanceStyle;

import java.util.List;

public interface DanceStyleRepository extends JpaRepository<DanceStyle, Long> {

    List<DanceStyle> findAll(Sort sort);

    DanceStyle findByName(String name);
}
