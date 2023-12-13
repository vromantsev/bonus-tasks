package ua.hillel.service;

import ua.hillel.entity.Student;

import java.util.List;

public interface GenericDao<T, ID> {

    void save(T entity);

    T findById(ID id);

    T findByEmail(String email);

    List<T> findAll();

    Student update(T entity);

    boolean deleteById(ID id);

}
