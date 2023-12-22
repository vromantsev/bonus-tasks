package ua.hillel.entity_relations.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * todo:
 * - configure JPA entity
 * - specify table name: "employee"
 * - configure auto generated identifier
 * - configure not nullable columns: email, firstName, lastName
 *
 * - map unidirectional relation between {@link Employee} and {@link EmployeeProfile} on the child side
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String fistName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Employee() {
    }

    public Employee(Long id, String email, String fistName, String lastName) {
        this.id = id;
        this.email = email;
        this.fistName = fistName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fistName='" + fistName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
