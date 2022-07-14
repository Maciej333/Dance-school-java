package mr.danceschool.service;

import mr.danceschool.entity.DanceStyle;

import java.util.List;

public interface DanceStyleService {

    public DanceStyle save(DanceStyle danceStyle);

    public DanceStyle findById(long id);

    public List<DanceStyle> findAll();

    public Integer deleteById(long id);

    public DanceStyle findByName(String name);
}
