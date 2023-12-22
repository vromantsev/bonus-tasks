package ua.hillel.homework.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import ua.hillel.homework.entity.Student;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class StudentDao implements GenericDao<Student, Long> {

    private final EntityManagerFactory emf;

    public StudentDao(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Student student) {
        Objects.requireNonNull(student, "Parameter [student] must not be null!");
        if (student.hasId()) {
            throw new IllegalArgumentException("A new student must have empty [id] field!");
        }
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });
    }

    @Override
    public Student findById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        return doInTxReturning(em -> em.find(Student.class, id));
    }

    @Override
    public Student findByEmail(String email) {
        Objects.requireNonNull(email, "Parameter [email] must not be null!");
        return doInTxReturning(em -> em.createQuery("FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", email)
                .getSingleResult()
        );
    }

    @Override
    public List<Student> findAll() {
        return doInTxReturning(em -> em.createQuery("FROM Student s", Student.class).getResultList());
    }

    @Override
    public Student update(Student student) {
        Objects.requireNonNull(student, "Parameter [student] must not be null!");
        if (!student.hasId()) {
            throw new IllegalArgumentException("Student should have an id assigned before being updated!");
        }
        return doInTxReturning(em -> em.merge(student));
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        return doInTxReturning(em -> {
            Student student = em.getReference(Student.class, id);
            if (student != null) {
                em.remove(student);
                return true;
            }
            return false;
        });
    }

    private <T> T doInTxReturning(Function<EntityManager, T> entityManagerFunction) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = entityManagerFunction.apply(em);
            tx.commit();
            return result;
        } catch (Exception ex) {
            tx.rollback();
            throw new RuntimeException(ex);
        }
    }
}
