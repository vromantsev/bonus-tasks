package ua.hillel.service;

import jakarta.persistence.EntityManagerFactory;
import ua.hillel.entity.Student;
import ua.hillel.exceptions.ExerciseIsNotCompletedException;

import java.util.List;

public class StudentDao implements GenericDao<Student, Long> {

    private final EntityManagerFactory emf;

    public StudentDao(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Student entity) {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }

    @Override
    public Student findById(Long id) {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }

    @Override
    public Student findByEmail(String email) {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }

    @Override
    public List<Student> findAll() {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }

    @Override
    public void update(Student entity) {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }

    @Override
    public boolean deleteById(Long id) {
        throw new ExerciseIsNotCompletedException("Exercise is not completed!");
    }
}
