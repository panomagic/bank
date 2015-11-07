import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import student.dao.StudentMapper;
import student.model.Student;
import javax.sql.DataSource;
import java.util.List;


public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "Spring-Module.xml");
        DataSource dataSource = (DataSource) appContext.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        /*Student newStudent = new Student();
        newStudent.setId(4);
        newStudent.setName("Sergey");
        newStudent.setEmail("mail@email.com");
        jdbcTemplate.update("INSERT INTO students (id, name, email) VALUES (?,?,?)", new Object[] {
                newStudent.getId(),
                newStudent.getName(),
                newStudent.getEmail()
        });*/

        List<Student> studentList = jdbcTemplate.query("SELECT * from students", new StudentMapper());

        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i).getName() + " | " + studentList.get(i).getEmail());
        }
    }
}