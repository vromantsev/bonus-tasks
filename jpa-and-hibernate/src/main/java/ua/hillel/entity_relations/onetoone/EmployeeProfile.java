package ua.hillel.entity_relations.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * todo:
 * - configure JPA entity
 * - specify table name: "employee_profile"
 * - configure not nullable columns: position, department
 *
 * - map relation between {@link Employee} and {@link EmployeeProfile} using foreign_key column: "employee_id"
 */
@Entity
@Table(name = "employee_profile")
public class EmployeeProfile {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "employee_id")
    @OneToOne
    private Employee employee;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "department", nullable = false)
    private String department;

    public EmployeeProfile() {
    }

    public EmployeeProfile(Long id, Employee employee, String position, String department) {
        this.id = id;
        this.employee = employee;
        this.position = position;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeProfile that = (EmployeeProfile) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EmployeeProfile{" +
                "id=" + id +
                ", employee=" + employee +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
