package mr.danceschool.service;

import mr.danceschool.entity.Student;

import java.util.List;

public interface StudentService {

    public Student save(Student student);

    public Student findById(long id);

    public List<Student> findAll();

    public Integer deleteById(long id);
}
