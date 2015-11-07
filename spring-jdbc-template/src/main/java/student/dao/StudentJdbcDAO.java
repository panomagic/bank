package student.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import student.model.Student;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentJdbcDAO extends JdbcDaoSupport implements StudentDAO {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveStudent(Student student) {
        this.getJdbcTemplate().update("insert into STUDENT (name) values (?)",new Object[] {student.getName()});
    }

    @Override
    public List<Student> getAllStudents() {
        return this.jdbcTemplate.query("SELECT * from students", new StudentMapper());
    }


}