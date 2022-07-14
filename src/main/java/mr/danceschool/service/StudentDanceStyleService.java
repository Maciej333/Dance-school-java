package mr.danceschool.service;

import mr.danceschool.entity.StudentDanceStyle;

import java.util.List;

public interface StudentDanceStyleService {

    public StudentDanceStyle save(StudentDanceStyle studentDanceStyle);

    public StudentDanceStyle findById(long id);

    public List<StudentDanceStyle> findAll();

    public Integer deleteById(long id);
}
