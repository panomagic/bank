import student.dao.StudentDAO;
import student.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class StudentTest {

    public static void main(String[] args) {
        ApplicationContext appContext = new FileSystemXmlApplicationContext("SpringConfig.xml");
        Student student = new Student();
        student.setName("AmitabhDao");

        StudentDAO studentDAO = (StudentDAO) appContext.getBean("studentDAO");
        studentDAO.saveStudent(student);


    }


}
