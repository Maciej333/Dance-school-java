package s18454.diploma.service;

import s18454.diploma.entity.StudentDanceStyle;

import java.util.List;

public interface StudentDanceStyleService {

    public StudentDanceStyle save(StudentDanceStyle studentDanceStyle);

    public StudentDanceStyle findById(long id);

    public List<StudentDanceStyle> findAll();

    public Integer deleteById(long id);
}
