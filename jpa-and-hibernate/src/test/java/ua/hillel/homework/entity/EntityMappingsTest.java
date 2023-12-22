package ua.hillel.homework.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.hillel.homework.entity.Homework;
import ua.hillel.homework.entity.Student;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityMappingsTest {

    private final Class<Student> studentClazz = Student.class;
    private final Class<Homework> homeworkClazz = Homework.class;

    @DisplayName("Student has annotation @Entity")
    @Test
    public void studentIsMarkedAsEntity() {
        assertTrue(studentClazz.isAnnotationPresent(Entity.class), "Class Student must be marked as an entity.");
    }

    @DisplayName("Student has annotation @Table with proper table name")
    @Test
    public void studentIsAnnotatedWithTableAnnotation() {
        Table table = studentClazz.getAnnotation(Table.class);
        assertNotNull(table, "Class Student must have proper table mapping.");
        String tableName = table.name();
        assertEquals("student", tableName, "Class Student must have a proper table name specified.");
    }

    @DisplayName("Student has annotation @Id & @GeneratedValue")
    @Test
    public void studentHasIdAndPKGenerationStrategy() throws NoSuchFieldException {
        Field id = studentClazz.getDeclaredField("id");
        assertTrue(id.isAnnotationPresent(Id.class), "Field id must be marked as a primary key!");
        assertTrue(id.isAnnotationPresent(GeneratedValue.class), "Primary key generation strategy must be set!");
    }

    @DisplayName("Student has first_name property mapped correctly")
    @Test
    public void studentHasFirstNamePropertyMappedCorrectly() throws NoSuchFieldException {
        Field firstName = studentClazz.getDeclaredField("firstName");
        Column column = firstName.getAnnotation(Column.class);
        assertNotNull(column, "Field 'firstName' must be annotated via @Column annotation");
        assertEquals("first_name", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "It's forbidden to have null values for the field 'firstName'");
    }

    @DisplayName("Student has last_name property mapped correctly")
    @Test
    public void studentHasLastNamePropertyMappedCorrectly() throws NoSuchFieldException {
        Field lastName = studentClazz.getDeclaredField("lastName");
        Column column = lastName.getAnnotation(Column.class);
        assertNotNull(column, "Field 'lastName' must be annotated via @Column annotation");
        assertEquals("last_name", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "It's forbidden to have null values for the field 'lastName'");
    }

    @DisplayName("Student has email property mapped correctly")
    @Test
    public void studentHasEmailPropertyMappedCorrectly() throws NoSuchFieldException {
        Field email = studentClazz.getDeclaredField("email");
        Column column = email.getAnnotation(Column.class);
        assertNotNull(column, "Field 'email' must be annotated via @Column annotation");
        assertEquals("email", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "It's forbidden to have null values for the field 'email'");
        assertTrue(column.unique(), "Email must be unique!");
    }

    @DisplayName("Student has proper one to many mapping")
    @Test
    public void studentHasProperOneToManyMapping() throws NoSuchFieldException {
        Field homeworks = studentClazz.getDeclaredField("homeworks");
        OneToMany oneToMany = homeworks.getAnnotation(OneToMany.class);
        assertNotNull(oneToMany, "Field 'homeworks' must represent one-to-many relationship on the parent side.");
        assertEquals("student", oneToMany.mappedBy(), "mappedBy parameter has incorrect value.");
        assertArrayEquals(new CascadeType[]{CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, oneToMany.cascade(), "Cascade types must be properly specified on the parent side!");
        assertTrue(oneToMany.orphanRemoval(), "Orphan removal must be specified!");
    }

    @DisplayName("Student has addHomework implemented")
    @Test
    public void studentHasAddHomeworkMethodImplemented() {
        Student student = new Student();
        student.setId(1L);
        student.setEmail("test@gmail.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        assertEquals(0, student.getHomeworks().size(), "Homeworks size should be empty!");
        student.addHomework(new Homework(1L, "dummy description", LocalDate.now(), 0, null));
        assertEquals(1, student.getHomeworks().size(), "Incorrect size of 'homeworks'");
    }

    @DisplayName("Student has removeHomework implemented")
    @Test
    public void studentHasRemoveHomeworkMethodImplemented() {
        Student student = new Student();
        student.setId(1L);
        student.setEmail("test@gmail.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        assertEquals(0, student.getHomeworks().size(), "Homeworks size should be empty!");
        student.addHomework(new Homework(1L, "dummy description", LocalDate.now(), 0, null));
        assertEquals(1, student.getHomeworks().size(), "Incorrect size of 'homeworks'");
        student.removeHomework(student.getHomeworks().iterator().next());
        assertEquals(0, student.getHomeworks().size(), "Incorrect size of 'homeworks'");
    }

    @DisplayName("Homework has annotation @Entity")
    @Test
    public void homeworkIsMarkedAsAnEntity() {
        assertTrue(homeworkClazz.isAnnotationPresent(Entity.class), "Class Homework must be annotated with @Entity annotation!");
    }

    @DisplayName("Homework has annotation @Table with proper table name")
    @Test
    public void homeworkIsAnnotatedWithTableAnnotation() {
        String expectedTableName = "homework";
        Table table = homeworkClazz.getAnnotation(Table.class);
        String tableName = table.name();
        assertNotNull(table, "Class Homework must be annotated with @Table annotation!");
        assertEquals(expectedTableName, tableName, "Table name must be properly specified!");
    }

    @DisplayName("Homework has annotation @Id & @GeneratedValue")
    @Test
    public void homeworkHasIdAndPKGenerationStrategy() throws NoSuchFieldException {
        Field id = homeworkClazz.getDeclaredField("id");
        assertTrue(id.isAnnotationPresent(Id.class), "Field id must be marked as a primary key!");
        assertTrue(id.isAnnotationPresent(GeneratedValue.class), "Primary key generation strategy must be set!");
    }

    @DisplayName("Homework has description property mapped correctly")
    @Test
    public void homeworkHasDescriptionMappedCorrectly() throws NoSuchFieldException {
        Field description = homeworkClazz.getDeclaredField("description");
        Column column = description.getAnnotation(Column.class);
        assertNotNull(column, "Field description must be annotated with @Column annotation!");
        assertEquals("description", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Null description is not allowed!");
    }

    @DisplayName("Homework has deadline property mapped correctly")
    @Test
    public void homeworkHasDeadlineMappedCorrectly() throws NoSuchFieldException {
        Field deadline = homeworkClazz.getDeclaredField("deadline");
        Column column = deadline.getAnnotation(Column.class);
        assertNotNull(column, "Field deadline must be annotated with @Column annotation!");
        assertEquals("deadline", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Null deadline is not allowed!");
    }

    @DisplayName("Homework has mark property mapped correctly")
    @Test
    public void homeworkHasMarkMappedCorrectly() throws NoSuchFieldException {
        Field mark = homeworkClazz.getDeclaredField("mark");
        Column column = mark.getAnnotation(Column.class);
        assertNotNull(column, "Field mark must be annotated with @Column annotation!");
        assertEquals("mark", column.name(), "Column name must be properly specified!");
    }

    @DisplayName("Homework has proper many-to-one mapping on the child side")
    @Test
    public void homeworkHasProperMappingOnTheChildSide() throws NoSuchFieldException {
        Field student = homeworkClazz.getDeclaredField("student");
        JoinColumn joinColumn = student.getAnnotation(JoinColumn.class);
        assertNotNull(joinColumn, "Field student must be annotated with @JoinColumn annotation!");
        assertEquals("student_id", joinColumn.name(), "Name of the joined column must be properly specified!");
        ManyToOne manyToOne = student.getAnnotation(ManyToOne.class);
        assertNotNull(manyToOne, "Field 'student' must represent many-to-one relationship on the child side.");
        assertEquals(FetchType.LAZY, manyToOne.fetch(), "FetchType.LAZY must be explicitly specified!");
    }
}
