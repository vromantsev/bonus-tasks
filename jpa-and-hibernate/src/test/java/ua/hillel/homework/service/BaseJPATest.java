package ua.hillel.homework.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.function.Function;

public class BaseJPATest {

    private static final String PERSISTENCE_UNIT = "test-persistence-unit";

    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void drop() {
        doInTxReturning(em -> em.createQuery("DELETE FROM Homework").executeUpdate());
        doInTxReturning(em -> em.createQuery("DELETE FROM Student").executeUpdate());
    }

    @AfterAll
    public static void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    protected <T> T doInTxReturning(Function<EntityManager, T> entityManagerFunction) {
        EntityManager em = entityManagerFactory.createEntityManager();
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
