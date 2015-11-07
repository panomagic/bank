import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import student.StudentMapper;
import student.model.Student;
import javax.sql.DataSource;
import java.util.List;


public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "Spring-Module.xml");
        DataSource dataSource = (DataSource) appContext.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Student> studentList = jdbcTemplate.query("SELECT * from students", new StudentMapper());

        for (int i = 0; i < studentList.size(); i++) {
            System.out.println(studentList.get(i).getName() + " | " + studentList.get(i).getEmail());
        }
    }
}