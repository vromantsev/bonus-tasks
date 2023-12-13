package ua.hillel.service;

import org.junit.jupiter.api.Test;
import ua.hillel.entity.Student;
import ua.hillel.utils.TestDataUtils;

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
        Student student = TestDataUtils.createStudent("j.l@spring.com");

        // when
        studentDao.save(student);

        // then
        Student createdStudent = doInTxReturning(em -> em.createQuery("FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", student.getEmail())
                .getSingleResult()
        );

        // assertions & verification
        assertNotNull(createdStudent);
        assertTrue(createdStudent.hasId());
    }

    @Test
    public void whenStudentIsNullThenFailWithException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> this.studentDao.save(null));
        assertEquals("Parameter [student] must not be null!", exception.getMessage());
    }

    @Test
    public void whenNewStudentHasIdBeforeSavingThenFailWithException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
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
        Student newStudent = TestDataUtils.createStudent("test@gmail.com");
        studentDao.save(newStudent);

        // when
        Student found = findStudent(newStudent.getEmail());

        // then
        Student student = this.studentDao.findById(found.getId());

        // assertions & verification
        assertNotNull(student);
    }

    @Test
    public void whenSearchForStudentByIdThenStudentNotFound() {
        // given
        Long fakeId = 123L;

        // then
        Student student = this.studentDao.findById(fakeId);

        // assertions & verification
        assertNull(student);
    }

    @Test
    public void whenSearchForStudentByEmailThenStudentIsFound() {
        // given
        Student student = TestDataUtils.createStudent("123-test@gmail.com");

        // when
        doInTxReturning(em -> {
            em.persist(student);
            return null;
        });

        // then
        Student studentFromDatabase = this.studentDao.findByEmail(student.getEmail());

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
        List<Student> studentsFromDatabase = this.studentDao.findAll();

        // assertions & verification
        assertNotNull(studentsFromDatabase);
        assertFalse(studentsFromDatabase.isEmpty());
        assertEquals(5, studentsFromDatabase.size());
    }

    @Test
    public void whenUpdateStudentThenStudentIsUpdatedSuccessfully() {
        // given

        // when

        // then

        // assertions & verification

    }

    // TODO update - fail if student == null
    // TODO update - fail if student_id == null

    // TODO delete - successful deletion
    // TODO delete - fail if student_id == null

    private Student findStudent(final String email) {
        return doInTxReturning(em -> em.createQuery("FROM Student", Student.class).getResultList()).stream()
                .filter(s -> s.getEmail().equals(email))
                .findAny()
                .orElseThrow();
    }
}
