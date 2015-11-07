package student.dao;

import student.model.Student;

import java.util.List;

public interface StudentDAO {
    void saveStudent(Student student);

    List<Student> getAllStudents();
}
