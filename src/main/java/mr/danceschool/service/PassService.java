package mr.danceschool.service;

import mr.danceschool.entity.Pass;

import java.util.List;

public interface PassService {

    public Pass save(Pass pass);

    public Pass findById(long id);

    public List<Pass> findAll();

    public Integer deleteById(long id);
}
