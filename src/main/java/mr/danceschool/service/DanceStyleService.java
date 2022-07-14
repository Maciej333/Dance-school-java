package s18454.diploma.service;

import s18454.diploma.entity.DanceStyle;

import java.util.List;

public interface DanceStyleService {

    public DanceStyle save(DanceStyle danceStyle);

    public DanceStyle findById(long id);

    public List<DanceStyle> findAll();

    public Integer deleteById(long id);

    public DanceStyle findByName(String name);
}
