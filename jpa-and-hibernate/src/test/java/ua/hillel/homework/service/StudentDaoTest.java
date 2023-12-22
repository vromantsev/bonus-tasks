package ua.hillel.homework.service;

import org.junit.jupiter.api.Test;
import ua.hillel.homework.entity.Student;
import ua.hillel.homework.utils.TestDataUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentDaoTest extends BaseJPATest {

    private final GenericDao<Student, Long> studentDao = new StudentDao(entityManagerFactory);

    @Test
    public void whenSaveStudentThenStudentIsSavedSuccessfully() {
        // given
        var student = TestDataUtils.createStudent("j.l@spring.com");

        // when
        studentDao.save(student);

        // then
        var createdStudent = doInTxReturning(em -> em.createQuery("FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", student.getEmail())
                .getSingleResult()
        );

        // assertions & verification
        assertNotNull(createdStudent);
        assertTrue(createdStudent.hasId());
    }

    @Test
    public void whenStudentIsNullThenFailWithException() {
        var exception = assertThrows(NullPointerException.class, () -> this.studentDao.save(null));
        assertEquals("Parameter [student] must not be null!", exception.getMessage());
    }

    @Test
    public void whenNewStudentHasIdBeforeSavingThenFailWithException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            Student student = new Student();
            student.setId(1L);
            student.setFirstName("Josh");
            student.setLastName("Long");
            student.setEmail("j.l@spring.com");
            this.studentDao.save(student);
        });
        assertEquals("A new student must have empty [id] field!", exception.getMessage());
    }

    @Test
    public void whenSearchForStudentByIdThenStudentIsFoundSuccessfully() {
        // given
        var newStudent = TestDataUtils.createStudent("test@gmail.com");
        studentDao.save(newStudent);

        // when
        var found = findStudent(newStudent.getEmail());

        // then
        var student = this.studentDao.findById(found.getId());

        // assertions & verification
        assertNotNull(student);
    }

    @Test
    public void whenSearchForStudentByIdThenStudentNotFound() {
        // given
        var fakeId = 123L;

        // then
        var student = this.studentDao.findById(fakeId);

        // assertions & verification
        assertNull(student);
    }

    @Test
    public void whenSearchForStudentByEmailThenStudentIsFound() {
        // given
        var student = TestDataUtils.createStudent("123-test@gmail.com");

        // when
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });

        // then
        var studentFromDatabase = this.studentDao.findByEmail(student.getEmail());

        // assertions & verification
        assertNotNull(studentFromDatabase);
        assertTrue(studentFromDatabase.hasId());
        assertAll(() -> {
            assertEquals(student.getFirstName(), studentFromDatabase.getFirstName());
            assertEquals(student.getLastName(), studentFromDatabase.getLastName());
            assertEquals(student.getEmail(), studentFromDatabase.getEmail());
            assertEquals(1, studentFromDatabase.getHomeworks().size());
        });
    }

    @Test
    public void whenSearchForAllStudentsThenStudentsFoundSuccessfully() {
        // given
        List<Student> students = TestDataUtils.getStudents();

        // when
        doInTxReturning(em -> {
            for (Student student : students) {
                em.persist(student);
            }
            return null;
        });

        // then
        var studentsFromDatabase = this.studentDao.findAll();

        // assertions & verification
        assertNotNull(studentsFromDatabase);
        assertFalse(studentsFromDatabase.isEmpty());
        assertEquals(5, studentsFromDatabase.size());
    }

    @Test
    public void whenUpdateStudentThenStudentIsUpdatedSuccessfully() {
        // given
        var student = TestDataUtils.createStudent("josh.long@spring.io");
        var firstNameUpdated = student.getFirstName() + " UPDATED";
        var lastNameUpdated = student.getLastName() + " UPDATED";

        // when
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });
        student.setFirstName(firstNameUpdated);
        student.setLastName(lastNameUpdated);

        // then
        var updatedStudent = this.studentDao.update(student);

        // assertions & verification
        assertNotNull(updatedStudent);
        assertEquals(firstNameUpdated, updatedStudent.getFirstName());
        assertEquals(lastNameUpdated, updatedStudent.getLastName());
    }

    @Test
    public void whenUpdateStudentAndPassNullArgumentThenThrowException() {
        // given
        var expectedExceptionMessage = "Parameter [student] must not be null!";

        // then
        var exception = assertThrows(NullPointerException.class, () -> this.studentDao.update(null));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void whenUpdateStudentWithNullIdThenThrowException() {
        // given
        var student = TestDataUtils.createStudent(null);
        var updated = "UPDATED";
        var expectedExceptionMessage = "Student should have an id assigned before being updated!";

        // when
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });

        // then
        student.setId(null);
        student.setFirstName(student.getLastName() + updated);
        student.setLastName(student.getLastName() + updated);
        var exception = assertThrows(IllegalArgumentException.class, () -> this.studentDao.update(student));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void whenDeleteStudentThenOperationSuccessful() {
        // given
        var student = TestDataUtils.createStudent(null);

        // when
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });
        Student studentFromDatabase = this.studentDao.findAll().stream()
                .filter(s -> s.getEmail().equals(student.getEmail()))
                .findAny()
                .orElseThrow();

        // then
        boolean isDeleted = this.studentDao.deleteById(studentFromDatabase.getId());

        // assertions & verification
        assertTrue(isDeleted);
    }

    @Test
    public void whenDeleteStudentWithNullIDThenThrowException() {
        // given
        var expectedExceptionMessage = "Parameter [id] must not be null!";

        // then
        var exception = assertThrows(NullPointerException.class, () -> this.studentDao.deleteById(null));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    private Student findStudent(final String email) {
        return doInTxReturning(em -> em.createQuery("FROM Student", Student.class).getResultList()).stream()
                .filter(s -> s.getEmail().equals(email))
                .findAny()
                .orElseThrow();
    }
}
