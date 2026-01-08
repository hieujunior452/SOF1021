package dao;

import java.util.List;

public interface Dao_CRUD<T, ID>{

    void create(T entity);

    void update(T entity);

    void deleteById(ID id);

    List<T> findAll();

    T findById(ID id);
}
    
