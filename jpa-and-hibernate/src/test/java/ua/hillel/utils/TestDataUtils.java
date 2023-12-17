package ua.hillel.utils;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import lombok.experimental.UtilityClass;
import ua.hillel.entity.Homework;
import ua.hillel.entity.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestDataUtils {

    private final Fairy fairy = Fairy.create();

    public List<Student> getStudents() {
        var students = new ArrayList<Student>();
        var maxSize = 5;
        for (int i = 0; i < maxSize; i++) {
            Person person = fairy.person();
            Student student = new Student();
            student.setFirstName(person.getFirstName());
            student.setLastName(person.getLastName());
            student.setEmail(person.getEmail());
            students.add(student);
        }
        return students;
    }

    public Student createStudent(final String email) {
        Student student = new Student();
        student.setFirstName("Josh");
        student.setLastName("Long");
        student.setEmail(email == null ? fairy.person().getEmail() : email);
        Homework homework = new Homework();
        homework.setDescription("test description");
        homework.setDeadline(LocalDate.now());
        student.addHomework(homework);
        return student;
    }
}
