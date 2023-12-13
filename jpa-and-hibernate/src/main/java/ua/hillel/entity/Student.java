package ua.hillel.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@ToString
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Builder.Default
    @OneToMany(
            mappedBy = "student",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private Set<Homework> homeworks = new HashSet<>();

    public void addHomework(final Homework homework) {
        homework.setStudent(this);
        homeworks.add(homework);
    }

    public void removeHomework(final Homework homework) {
        homework.setStudent(null);
        homeworks.remove(homework);
    }

    public boolean hasId() {
        return id != null;
    }
}
