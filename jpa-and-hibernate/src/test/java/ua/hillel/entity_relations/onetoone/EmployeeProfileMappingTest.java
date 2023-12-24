package ua.hillel.entity_relations.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeProfileMappingTest {

    private final Class<Employee> employeeClass = Employee.class;
    private final Class<EmployeeProfile> employeeProfileClass = EmployeeProfile.class;

    // TODO Test description: configure class Employee as JPA entity
    @DisplayName("Class Employee is configured as JPA entity")
    @Test
    public void classEmployeeIsConfiguredAsJPAEntity() {
        assertTrue(employeeClass.isAnnotationPresent(Entity.class), "Class Employee must be configured as JPA entity!");
    }

    // TODO Test description: specify table name 'employee'
    @DisplayName("Class Employee has proper table name")
    @Test
    public void classEmployeeHasProperTableName() {
        Table table = employeeClass.getAnnotation(Table.class);
        assertNotNull(table, "Class Employee should be marked with @Table annotation!");
        assertEquals("employee", table.name(), "Table name must be properly specified!");
    }

    // TODO Test description: mark id field as primary key and set automatic id generation strategy for it
    @DisplayName("Class Employee has id field configured as primary key")
    @Test
    public void classEmployeeHasIdFieldConfiguredAsPrimaryKey() throws NoSuchFieldException {
        Field id = employeeClass.getDeclaredField("id");
        assertTrue(id.isAnnotationPresent(Id.class), "Field 'id' must be configured as a primary key!");
        assertTrue(id.isAnnotationPresent(GeneratedValue.class), "Automatic id generation strategy must be set!");
    }

    // TODO Test description: specify explicitly column name 'email', it must be mandatory and unique
    @DisplayName("Class Employee has properly mapped email field")
    @Test
    public void classEmployeeHasProperlyMappedEmailField() throws NoSuchFieldException {
        Field email = employeeClass.getDeclaredField("email");
        Column column = email.getAnnotation(Column.class);
        assertNotNull(column, "Field email must be marked with @Column annotation!");
        assertEquals("email", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Column must be mandatory!");
        assertTrue(column.unique(), "Column must be unique!");
    }

    // TODO Test description: specify explicitly column name 'first_name', a column must be mandatory
    @DisplayName("Class Employee has properly mapped firstName field")
    @Test
    public void classEmployeeHasProperlyMappedFirstNameField() throws NoSuchFieldException {
        Field firstName = employeeClass.getDeclaredField("firstName");
        Column column = firstName.getAnnotation(Column.class);
        assertNotNull(column, "Field firstName must be marked with @Column annotation!");
        assertEquals("first_name", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Column must be mandatory!");
    }

    // TODO Test description: specify explicitly column name 'last_name', a column must be mandatory
    @DisplayName("Class Employee has properly mapped lastName field")
    @Test
    public void classEmployeeHasProperlyMappedLastNameField() throws NoSuchFieldException {
        Field lastName = employeeClass.getDeclaredField("lastName");
        Column column = lastName.getAnnotation(Column.class);
        assertNotNull(column, "Field lastName must be marked with @Column annotation!");
        assertEquals("last_name", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Column must be mandatory!");
    }

    // TODO Test description: configure class Employee as JPA entity
    @DisplayName("Class EmployeeProfile is configured as JPA entity")
    @Test
    public void classEmployeeProfileIsConfiguredAsJPAEntity() {
        assertTrue(employeeProfileClass.isAnnotationPresent(Entity.class), "Class EmployeeProfile must be configured as JPA entity!");
    }

    // TODO Test description: specify table name 'employee_profile'
    @DisplayName("Class EmployeeProfile has proper table name")
    @Test
    public void classEmployeeProfileHasProperTableName() {
        Table table = employeeProfileClass.getAnnotation(Table.class);
        assertNotNull(table, "Class EmployeeProfile should be marked with @Table annotation!");
        assertEquals("employee_profile", table.name(), "Table name must be properly specified!");
    }

    // TODO Test description: mark id field as primary key and set automatic id generation strategy for it
    @DisplayName("Class EmployeeProfile has id field configured as primary key")
    @Test
    public void classEmployeeProfileHasIdFieldConfiguredAsPrimaryKey() throws NoSuchFieldException {
        Field id = employeeProfileClass.getDeclaredField("id");
        assertTrue(id.isAnnotationPresent(Id.class), "Field 'id' must be configured as a primary key!");
        assertTrue(id.isAnnotationPresent(GeneratedValue.class), "Automatic id generation strategy must be set!");
    }

    // TODO Test description: map relation between Employee and EmployeeProfile using foreign_key column 'employee_id'
    @DisplayName("Class EmployeeProfile has properly mapped relation using foreign key column")
    @Test
    public void classEmployeeProfileHasProperlyMappedRelationUsingForeignKeyColumn() throws NoSuchFieldException {
        Field employee = employeeProfileClass.getDeclaredField("employee");
        JoinColumn joinColumn = employee.getDeclaredAnnotation(JoinColumn.class);
        assertNotNull(joinColumn, "Foreign key column must be specified!");
        assertEquals("employee_id", joinColumn.name(), "Foreign key column name must be properly specified!");
        OneToOne oneToOne = employee.getDeclaredAnnotation(OneToOne.class);
        assertNotNull(oneToOne, "Relation between Employee and EmployeeProfile must be specified!");
    }

    // TODO Test description: specify explicitly column name 'position', make the column mandatory
    @DisplayName("Class EmployeeProfile has properly mapped position field")
    @Test
    public void classEmployeeProfileHasProperlyMappedPositionField() throws NoSuchFieldException {
        Field position = employeeProfileClass.getDeclaredField("position");
        Column column = position.getAnnotation(Column.class);
        assertNotNull(column, "Field position must be marked with @Column annotation!");
        assertEquals("position", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Column must be mandatory!");
    }

    // TODO Test description: specify explicitly column name 'department', make the column mandatory
    @DisplayName("Class EmployeeProfile has properly mapped department field")
    @Test
    public void classEmployeeProfileHasProperlyMappedDepartmentField() throws NoSuchFieldException {
        Field department = employeeProfileClass.getDeclaredField("department");
        Column column = department.getAnnotation(Column.class);
        assertNotNull(column, "Field department must be marked with @Column annotation!");
        assertEquals("department", column.name(), "Column name must be properly specified!");
        assertFalse(column.nullable(), "Column must be mandatory!");
    }
}
